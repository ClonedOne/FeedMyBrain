package com.feedmybrain.feedlyclient;

import java.util.LinkedList;

/**
 * Created by blackecho on 16/05/15.
 */
public class Stream {

    private String id;
    private String title;
    private LinkedList<Article> articles;

    public Stream(String id, String title, LinkedList<Article> articles) {
        this.id = id;
        this.title = title;
        this.articles = articles;
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

    public LinkedList<Article> getArticles() {
        return articles;
    }

    public void setArticles(LinkedList<Article> articles) {
        this.articles = articles;
    }

}
