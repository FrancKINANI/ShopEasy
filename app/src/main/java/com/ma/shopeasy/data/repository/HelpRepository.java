package com.ma.shopeasy.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.ma.shopeasy.domain.model.ContactMessage;
import com.ma.shopeasy.domain.model.FAQArticle;
import com.ma.shopeasy.utils.Resource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HelpRepository {

    private final FirebaseFirestore firestore;

    @Inject
    public HelpRepository(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    public LiveData<Resource<List<FAQArticle>>> getFAQArticles() {
        MutableLiveData<Resource<List<FAQArticle>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        firestore.collection("faq_articles")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<FAQArticle> articles = queryDocumentSnapshots.toObjects(FAQArticle.class);
                    result.setValue(Resource.success(articles));
                })
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }

    public LiveData<Resource<Void>> sendContactMessage(ContactMessage message) {
        MutableLiveData<Resource<Void>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        firestore.collection("contact_messages")
                .document(message.getId())
                .set(message)
                .addOnSuccessListener(aVoid -> result.setValue(Resource.success(null)))
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }

    public LiveData<Resource<List<ContactMessage>>> getContactMessages() {
        MutableLiveData<Resource<List<ContactMessage>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        firestore.collection("contact_messages")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<ContactMessage> messages = queryDocumentSnapshots.toObjects(ContactMessage.class);
                    result.setValue(Resource.success(messages));
                })
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }
}
