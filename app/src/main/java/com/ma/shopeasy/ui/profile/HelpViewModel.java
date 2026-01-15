package com.ma.shopeasy.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ma.shopeasy.data.repository.HelpRepository;
import com.ma.shopeasy.domain.model.ContactMessage;
import com.ma.shopeasy.domain.model.FAQArticle;
import com.ma.shopeasy.utils.Resource;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HelpViewModel extends ViewModel {

    private final HelpRepository repository;

    @Inject
    public HelpViewModel(HelpRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<List<FAQArticle>>> getFAQArticles() {
        return repository.getFAQArticles();
    }

    public LiveData<Resource<Void>> sendContactMessage(ContactMessage message) {
        return repository.sendContactMessage(message);
    }

    public LiveData<Resource<List<ContactMessage>>> getContactMessages() {
        return repository.getContactMessages();
    }
}
