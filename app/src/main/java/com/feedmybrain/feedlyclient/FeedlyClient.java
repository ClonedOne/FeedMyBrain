package com.feedmybrain.feedlyclient;

import android.util.Log;

import com.feedmybrain.interfaces.RestResponse;
import com.feedmybrain.rest.JsonRestRequest;
import com.feedmybrain.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by blackecho on 16/05/15.
 */
public class FeedlyClient {

    private RestResponse context; // the context from which the client is created
    private String authToken; // authentication token
    private HashMap<String, String> headers;

    /*
        Public constructor
     */
    public FeedlyClient(String authToken, RestResponse context) {
        this.authToken = authToken;
        this.context = context;
        this.headers = new HashMap<String, String>();
    }

    /*
        Wrapper method to get the categories
     */
    public void categoriesAPI() {
        headers.put("Authorization", "OAuth " + authToken); // setting the authorization token
        JsonRestRequest request = new JsonRestRequest();
        request.setDelegate(context);
        request.setRequestDescription(Constants.categoriesRequestDesc);
        request.setRequestType(Constants.getRequestType);
        request.setRequestHeader(headers);
        request.execute(Constants.categoriesRequestAPI);
    }

    /*
        Wrapper method to get the subscriptions
     */
    public void subscriptionsAPI() {
        headers.put("Authorization", "OAuth " + authToken); // setting the authorization token
        JsonRestRequest request = new JsonRestRequest();
        request.setDelegate(context);
        request.setRequestDescription(Constants.subscriptionsRequestDesc);
        request.setRequestType(Constants.getRequestType);
        request.setRequestHeader(headers);
        request.execute(Constants.subscriptionsRequestAPI);
    }

    /*
        Wrapper method to get the articles of a subscriptions
     */
    public void subscriptionStreamAPI(Subscription subsc) {
        headers.put("Authorization", "OAuth " + authToken); // setting the authorization token
        JsonRestRequest request = new JsonRestRequest();
        request.setDelegate(context);
        request.setRequestDescription(Constants.subscriptionStreamDesc);
        request.setRequestType(Constants.getRequestType);
        request.setRequestHeader(headers);
        request.execute(Constants.subscriptionStreamAPI + "?" + "streamId=" + subsc.getId() + "&" + "count=" + Constants.subscriptionStreamCount);
    }

    /*
        Wrapper method to mark an article as read
     */
    public void markAsReadAPI(Article article) {
        headers.put("Authorization", "OAuth " + authToken); // setting the authorization token
        JsonRestRequest request = new JsonRestRequest();
        request.setDelegate(context);
        request.setRequestDescription(Constants.markersDesc);
        request.setRequestType(Constants.postRequestType);
        request.setRequestHeader(headers);
        JSONObject json = new JSONObject();
        try {
            json.put("action", "markAsRead");
            JSONArray ids = new JSONArray();
            ids.put(article.getId());
            json.put("entryIds", ids);
            json.put("type", "entries");
        } catch(JSONException je) {
            je.printStackTrace();
        }
        request.setJson(json.toString());
        request.execute(Constants.markersAPI);
    }

}