# ShopEasy Diagrams

## Use Case Diagram

```mermaid
usecaseDiagram
    actor "User" as U
    actor "Guest" as G
    actor "Firebase Auth" as FA
    actor "Stripe (Mock)" as S

    package ShopEasy {
        usecase "Browse Products" as UC1
        usecase "Search & Filter" as UC2
        usecase "View Details" as UC3
        usecase "Add to Cart" as UC4
        usecase "Login / Sign up" as UC5
        usecase "Place Order" as UC6
        usecase "View History" as UC7
        usecase "Manage Profile" as UC8
    }

    G --> UC1
    G --> UC2
    G --> UC3
    G --> UC5
    
    U --> UC1
    U --> UC2
    U --> UC3
    U --> UC4
    U --> UC6
    U --> UC7
    U --> UC8

    UC5 -- FA : authenticates
    UC6 -- S : payment processing
```

## Class Diagram

```mermaid
classDiagram
    class MainActivity {
        -NavController navController
        -ActivityMainBinding binding
    }

    class BaseFragment {
        <<abstract>>
    }

    class HomeFragment {
        -HomeViewModel viewModel
        -ProductAdapter adapter
    }

    class ProductDetailFragment {
        -ProductDetailViewModel viewModel
    }

    class HomeViewModel {
        -ProductRepository repository
        +LiveData products
    }

    class ProductRepository {
        -FirebaseFirestore firestore
        -ProductDao productDao
        +fetchProducts()
    }

    class ProductDao {
        <<interface>>
        +insertAll()
        +getAll()
    }

    class User {
        +String uid
        +String email
        +Address address
    }

    class Product {
        +String id
        +String name
        +double price
    }

    HomeFragment --|> BaseFragment
    ProductDetailFragment --|> BaseFragment
    HomeFragment ..> HomeViewModel
    HomeViewModel ..> ProductRepository
    ProductRepository ..> ProductDao
    ProductRepository ..> Product
```

## Order Flow (Sequence Diagram)

```mermaid
sequenceDiagram
    participant U as User
    participant V as CartViewModel
    participant R as OrderRepository
    participant F as Firestore
    participant S as Stripe (Mock)

    U->>V: Click Checkout
    V->>R: placeOrder(order)
    R->>S: processPayment(total)
    S-->>R: paymentSuccess
    R->>F: Create Order Document
    F-->>R: DocumentCreated
    R-->>V: Resource.Success(orderId)
    V-->>U: Show Confirmation Toast
```
