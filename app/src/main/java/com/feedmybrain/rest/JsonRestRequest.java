package com.feedmybrain.rest;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import com.feedmybrain.interfaces.RestResponse;
import com.feedmybrain.util.Constants;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class JsonRestRequest extends AsyncTask<String, String, String> {

	// Request parameters
	private String json;
	// Delegate to handle the request response
	private RestResponse delegate;
	// Request description used to discriminate between different calls on the same delegate
	private String requestDescription;
	// Request type (GET, POST)
	private String requestType;
    // Request header
    private HashMap<String, String> requestHeader;

	
	public JsonRestRequest() {}
	
	public JsonRestRequest(String json, RestResponse delegate, String requestDescription, String requestType, HashMap<String, String> requestHeader) {
		this.json = json;
		this.delegate = delegate;
		this.requestDescription = requestDescription;
		this.requestType = requestType;
        this.requestHeader = requestHeader;
	}
	
	public void setJson(String json) {
		this.json = json;
	}
	
	public void setDelegate(RestResponse delegate) {
		this.delegate = delegate;
	}
	
	public void setRequestDescription(String requestDescription) {
		this.requestDescription = requestDescription;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

    public void setRequestHeader(HashMap<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }
	
	@Override
	protected String doInBackground(String... uri) {
		AndroidHttpClient httpClient = AndroidHttpClient.newInstance("default");
		HttpResponse response = null;
		String responseString = null;
		StatusLine statusLine = null;
		try {
            if (requestType.equals(Constants.postRequestType)) {
                HttpPost postReq = new HttpPost(uri[0]);
                StringEntity se = new StringEntity(json);
                postReq.setEntity(se);
                postReq.setHeader("Accept", "application/json");
                postReq.setHeader("Content-Type", "application/json");
                if (requestHeader != null) {
                    for (HashMap.Entry<String, String> entry : requestHeader.entrySet()) {
                        postReq.setHeader(entry.getKey(), entry.getValue());
                    }
                }
                response = httpClient.execute(postReq);
                statusLine = response.getStatusLine();
            } else if (requestType.equals(Constants.getRequestType)) {
                HttpGet getReq = new HttpGet(uri[0]);
                if (requestHeader != null) {
                    for (HashMap.Entry<String, String> entry : requestHeader.entrySet()) {
                        getReq.setHeader(entry.getKey(), entry.getValue());
                    }
                }
                response = httpClient.execute(getReq);
                statusLine = response.getStatusLine();
            }
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();
                httpClient.close();
			} else {
				// Closes the connection.
				httpClient.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();	
		} catch (IOException e) {
			Log.d(Constants.DEBUG_NETWORK_TAG, ((statusLine == null) ? "null" : statusLine.getStatusCode()) + " - " + ((statusLine == null) ? "null" : statusLine.getReasonPhrase()));
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseString;
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		delegate.processFinish(result, requestDescription);
	}
	
}
