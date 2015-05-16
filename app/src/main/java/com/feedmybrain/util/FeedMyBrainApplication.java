package com.feedmybrain.util;

import android.app.Application;

import com.feedmybrain.feedlyclient.FeedlyFeed;

/**
 * Created by blackecho on 16/05/15.
 */
public class FeedMyBrainApplication extends Application {

    private FeedlyFeed feed;

    public FeedlyFeed getFeed() {
        return feed;
    }

    public void setFeed(FeedlyFeed feed) {
        this.feed = feed;
    }

}
