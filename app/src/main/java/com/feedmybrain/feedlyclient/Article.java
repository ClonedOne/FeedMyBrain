package com.feedmybrain.feedlyclient;

import java.util.LinkedList;

/**
 * Created by blackecho on 16/05/15.
 */
public class Article {

    private String id;
    private LinkedList<String> keywords;
    private String title;
    private Long published;
    private String author;
    private LinkedList<Category> categories;
    private String content;
    private String website;

    public Article(String id, LinkedList<String> keywords, String title, Long published, String author, LinkedList<Category> categories, String content, String website) {
        this.id = id;
        this.keywords = keywords;
        this.title = title;
        this.published = published;
        this.author = author;
        this.categories = categories;
        this.content = content;
        this.website = website;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(LinkedList<String> keywords) {
        this.keywords = keywords;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPublished() {
        return published;
    }

    public void setPublished(Long published) {
        this.published = published;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LinkedList<Category> getCategories() {
        return categories;
    }

    public void setCategories(LinkedList<Category> categories) {
        this.categories = categories;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String toString(){
        String res ="";
        res = res + "Article from: " + getWebsite() + ". From author: " + getAuthor() + ". " +
                "Title: " + getTitle() + ". ";
        return res;
    }

}
