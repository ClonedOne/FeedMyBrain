package com.feedmybrain.feedlyclient;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by blackecho on 16/05/15.
 */
public class FeedlyFeed {

    private LinkedList<Category> categories;
    private LinkedList<Subscription> subscriptions;
    private HashMap<String, LinkedList<Article>> articlesFeed;

    public FeedlyFeed() {
        this.categories = new LinkedList<Category>();
        this.subscriptions = new LinkedList<Subscription>();
        this.articlesFeed = new HashMap<String, LinkedList<Article>>();
    }

    public LinkedList<Category> getCategories() {
        return categories;
    }

    public LinkedList<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public HashMap<String, LinkedList<Article>> getArticlesFeed() {
        return articlesFeed;
    }

    public void setCategories(LinkedList<Category> categories) {
        this.categories = categories;
    }

    public void setSubscriptions(LinkedList<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public void setArticlesFeed(HashMap<String, LinkedList<Article>> articlesFeed) {
        this.articlesFeed = articlesFeed;
    }

}
