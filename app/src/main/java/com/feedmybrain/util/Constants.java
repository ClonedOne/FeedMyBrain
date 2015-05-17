package com.feedmybrain.util;

/**
 * Created by blackecho on 16/05/15.
 */
public class Constants {

    /*
        feedly api cconstants
     */
    public static final String feedlyApi = "http://cloud.feedly.com/"; // feedly api end-point
    public static final String authToken = "Ai_JI_17ImEiOiJGZWVkbHkgRGV2ZWxvcGVyIiwiZSI6MTQzOTYxNTc1NDU5MSwiaSI6IjNlZDAxMDg0LTU3YzgtNDVlOC1hZTcwLTBkNTVhZDBmYTg3MSIsInAiOjcsInQiOjEsInYiOiJwcm9kdWN0aW9uIiwidyI6IjIwMTUuMjEiLCJ4Ijoic3RhbmRhcmQifQ:feedlydev"; // authentication token
    public static final String categoriesRequestAPI = feedlyApi + "v3/categories";
    public static final String subscriptionsRequestAPI = feedlyApi + "v3/subscriptions";
    public static final String subscriptionStreamAPI = feedlyApi + "v3/streams/contents";
    public static final String markersAPI = feedlyApi + "v3/markers";


    /*
        api requests constants
     */
    public static final String categoriesRequestDesc = "categories_request";
    public static final String subscriptionsRequestDesc = "subscriptions_request";
    public static final String subscriptionStreamDesc = "subscriptions_stream_request";
    public static final int subscriptionStreamCount = 2;
    public static final String markersDesc = "markers_request";

    /*
        request types
     */
    public static final String postRequestType = "post";
    public static final String getRequestType = "get";

    /*
        debug strings
     */
    public static final String DEBUG_NETWORK_TAG = "NetworkDebug";
    public static final String DEBUG_FEEDLY = "FeedlyDebug";

}
