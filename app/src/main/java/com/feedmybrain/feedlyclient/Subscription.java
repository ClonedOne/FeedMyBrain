package com.feedmybrain.feedlyclient;

import java.util.LinkedList;

/**
 * Created by blackecho on 16/05/15.
 */
public class Subscription {

    private String id;
    private String title;
    private String website;
    private LinkedList<Category> categories;

    public Subscription(String id, String title, String website, LinkedList<Category> categories) {
        this.id = id;
        this.title = title;
        this.website = website;
        this.categories = categories;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public LinkedList<Category> getCategories() {
        return categories;
    }

    public void setCategories(LinkedList<Category> categories) {
        this.categories = categories;
    }

}
