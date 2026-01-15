package com.ma.shopeasy.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.ma.shopeasy.data.local.ProductDao;
import com.ma.shopeasy.domain.model.Product;
import com.ma.shopeasy.utils.Resource;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ProductRepository {

    private final FirebaseFirestore firestore;
    private final ProductDao productDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    @Inject
    public ProductRepository(FirebaseFirestore firestore, ProductDao productDao) {
        this.firestore = firestore;
        this.productDao = productDao;
    }

    public LiveData<List<Product>> getLocalProducts() {
        return productDao.getAllProducts();
    }

    public LiveData<Resource<List<Product>>> fetchRemoteProducts() {
        MutableLiveData<Resource<List<Product>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        firestore.collection("products")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Product> products = task.getResult().toObjects(Product.class);
                        if (products.isEmpty()) {
                            populateDummyData();
                        } else {
                            result.setValue(Resource.success(products));
                            // Update cache
                            executor.execute(() -> {
                                productDao.deleteAllProducts();
                                productDao.insertProducts(products);
                            });
                        }
                    } else {
                        result.setValue(Resource.error(task.getException() != null ? task.getException().getMessage()
                                : "Failed to fetch products"));
                    }
                });

        return result;
    }

    private void populateDummyData() {
        for (int i = 1; i <= 10; i++) {
            Product p = new Product("p" + i, "Product " + i, "Description for product " + i, i * 10.0, "Category",
                    "https://via.placeholder.com/150", 4.5f, 100, "Supplier", "IN_STOCK");
            firestore.collection("products").document(p.getId()).set(p);
        }
    }

    public LiveData<Product> getProductById(String id) {
        return productDao.getProductById(id);
    }

    public LiveData<Resource<Void>> updateProduct(Product product) {
        MutableLiveData<Resource<Void>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        firestore.collection("products")
                .document(product.getId())
                .set(product)
                .addOnSuccessListener(aVoid -> {
                    result.setValue(Resource.success(null));
                    // Update cache
                    executor.execute(() -> productDao.insertProduct(product));
                })
                .addOnFailureListener(e -> {
                    result.setValue(Resource.error(e.getMessage()));
                });

        return result;
    }
}
