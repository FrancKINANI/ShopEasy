package com.ma.shopeasy.domain.model;

import java.io.Serializable;

public class FAQArticle implements Serializable {
    private String id;
    private String title;
    private String content;
    private String category; // "Delivery", "Returns", "Payment", "Guides"

    public FAQArticle() {
        // Required for Firebase
    }

    public FAQArticle(String id, String title, String content, String category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
