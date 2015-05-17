package com.feedmybrain;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.feedmybrain.feedlyclient.Article;
import com.feedmybrain.feedlyclient.Category;
import com.feedmybrain.feedlyclient.FeedlyClient;
import com.feedmybrain.feedlyclient.FeedlyFeed;
import com.feedmybrain.feedlyclient.Subscription;
import com.feedmybrain.interfaces.RestResponse;
import com.feedmybrain.texttospeech.Speaker;
import com.feedmybrain.util.BTListenThread;
import com.feedmybrain.util.Constants;
import com.feedmybrain.util.FeedMyBrainApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.feedmybrain.util.NewsAdapter;
import com.feedmybrain.util.SpeakThread;
import com.neurosky.thinkgear.TGDevice;
import com.neurosky.thinkgear.*;

public class MainActivity extends Activity implements RestResponse {

    protected int blink = 0;
    protected int attention = 0;
    protected boolean inReading = false;
    protected boolean goOn = true;
    private Speaker speaker; // speaker object
    private TextToSpeech tts;

    private Button toggle; // button to toggle the speech

    private FeedlyClient feedlyClient; // client to feedly api
    private FeedlyFeed feed; // object that contains feedly data

    private String textToRead;
    private String spokenText;
    public TGDevice tgDevice;
    private BluetoothAdapter btAdapter;
    private MyBTHandler handler;
    private static final int SPEECH_REQUEST_CODE = 0;

    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggle = (Button)findViewById(R.id.speechToggle);
	    textToRead = "Text to be read";


        //card view initialization
        mRecyclerView = (RecyclerView) findViewById(R.id.news_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //checkTTS();
	    speaker = new Speaker(this);
        speaker.allow(true);
        tts = speaker.getTts();
        tts.setOnUtteranceProgressListener(
                new UtteranceProgressListener() {
                    @Override
                    public void onStart(String utteranceId) {

                    }

                    @Override
                    public void onDone(String utteranceId) {
                        if (utteranceId.equals("Do you want to continue reading?")) {
                            speaker.stop();
                            displaySpeechRecognizer();
                        }
                    }

                    @Override
                    public void onError(String utteranceId) {

                    }
                }
        );


       // handler = new MyBTHandler(this);

      /*  btAdapter = BluetoothAdapter.getDefaultAdapter();
        if(btAdapter != null) {
            Log.d("BTOOTH","Bluettoth adapter is NOT null");
            tgDevice = new TGDevice(btAdapter, handler);
            tgDevice.connect(false);
        }
        else {
            Log.d("BTOOTH","Bluettoth adapter is NULL");
        }*/

        BTListenThread btThread = new BTListenThread(this);
        btThread.run();

        displaySpeechRecognizer();

        // Creating global feed
        FeedMyBrainApplication app = (FeedMyBrainApplication) this.getApplication();
        app.setFeed(new FeedlyFeed());
        // Get the just created feed for this activity
        feed = app.getFeed();

        getArticles();

    }

    private void getArticles(){
        feedlyClient = new FeedlyClient(Constants.authToken, this);
        feedlyClient.categoriesAPI();
        feedlyClient.subscriptionsAPI();
    }

    public void processFinish(String response, String requestDescription) {
        if (requestDescription.equals(Constants.categoriesRequestDesc)) {
            Log.d(Constants.DEBUG_NETWORK_TAG, "Categories request finished");
            Log.d(Constants.DEBUG_NETWORK_TAG, ""+response);
            // Categories request
            try {
                if (response != null) {
                    Log.d(Constants.DEBUG_NETWORK_TAG, "Categories != null");
                    JSONArray categories = new JSONArray(response);
                    for (int i = 0; i < categories.length(); i++) {
                        JSONObject cat = categories.getJSONObject(i);
                        Category c = new Category(cat.getString("id"), cat.getString("label"));
                        feed.getCategories().add(c);
                    }
                }
            } catch (JSONException je) {
                je.printStackTrace();
            }
        } else if (requestDescription.equals(Constants.subscriptionsRequestDesc)) {
            Log.d(Constants.DEBUG_NETWORK_TAG, "Subscriptions request finished");
            Log.d(Constants.DEBUG_NETWORK_TAG, ""+response);
            // Subscriptions request
            try {
                if (response != null) {
                    Log.d(Constants.DEBUG_NETWORK_TAG, "Subscriptions != null");
                    JSONArray subscriptions = new JSONArray(response);
                    for (int i = 0; i < subscriptions.length(); i++) {
                        JSONObject subsc = subscriptions.getJSONObject(i);
                        // getting the categories list
                        JSONArray categs = subsc.getJSONArray("categories");
                        LinkedList<Category> cats = new LinkedList<Category>();
                        for (int j = 0; j < categs.length(); j++) {
                            JSONObject cat = categs.getJSONObject(j);
                            Category c = new Category(cat.getString("id"), cat.getString("label"));
                            cats.add(c);
                        }
                        // get website
                        String website = "";
                        if (subsc.has("website")) {
                            website = subsc.getString("website");
                        }
                        Subscription s = new Subscription(subsc.getString("id"), subsc.getString("title"), website, cats);
                        feed.getSubscriptions().add(s);
                    }
                    // Now we get the feed for each subscription
                    for (Subscription subsc : feed.getSubscriptions()) {
                        feedlyClient.subscriptionStreamAPI(subsc);
                    }
                }
            } catch (JSONException je) {
                je.printStackTrace();
            }
        } else if (requestDescription.equals(Constants.subscriptionStreamDesc)) {
            Log.d(Constants.DEBUG_NETWORK_TAG, "Stream request finished");
            Log.d(Constants.DEBUG_NETWORK_TAG, ""+response);
            // Stream request
            try {
                if (response != null) {
                    Log.d(Constants.DEBUG_NETWORK_TAG, "stream != null");
                    JSONObject stream = new JSONObject(response);
                    String subscription = stream.getString("id");
                    String website = stream.getString("title");
                    LinkedList<Article> articles = new LinkedList<Article>();
                    JSONArray items = stream.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject streamElem = items.getJSONObject(i);
                        LinkedList<String> ks = new LinkedList<String>();
                        // getting the keywords list
                        if (streamElem.has("keywords")) {
                            JSONArray keywords = streamElem.getJSONArray("keywords");
                            for (int j = 0; j < keywords.length(); j++) {
                                String k = keywords.getString(j);
                                ks.add(k);
                            }
                        }
                        // getting the categories list
                        JSONArray categs = streamElem.getJSONArray("categories");
                        LinkedList<Category> cats = new LinkedList<Category>();
                        for (int j = 0; j < categs.length(); j++) {
                            JSONObject cat = categs.getJSONObject(j);
                            Category c = new Category(cat.getString("id"), cat.getString("label"));
                            cats.add(c);
                        }
                        // getting the author
                        String author = "";
                        if (streamElem.has("author")) {
                            author = streamElem.getString("author");
                        }
                        // getting the content
                        String content = "";
                        if (streamElem.has("content")) {
                            content = streamElem.getJSONObject("content").getString("content");
                        }
                        Article art = new Article(streamElem.getString("id"), ks, streamElem.getString("title"), streamElem.getLong("published"), author, cats, content, website);
                        articles.add(art);
                    }
                    feed.getArticlesFeed().put(subscription, articles);
                }
            } catch (JSONException je) {
                je.printStackTrace();
            }
        } else if (requestDescription.equals(Constants.markersDesc)) {
            Log.d("Mark as Read", response);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        tgDevice.close();
        speaker.destroy();
        super.onDestroy();
    }

    public void speakit(View view) {
        getArticles();
        displaySpeechRecognizer();
    }
    public void speakBrain () throws Exception{
        LinkedList<Article> articles = new LinkedList<>();
        String curKey = "";
        inReading = true;
        //goOn = true;
        //while (goOn) {
            for (HashMap.Entry<String, LinkedList<Article>> entry : feed.getArticlesFeed().entrySet()) {
                if (goOn == false)
                    break;
                articles = entry.getValue();
                curKey = entry.getKey();
                for (Article art : articles) {
                    // Log.d("ARTICLEDBG", "article is = " + art.toString());
                    speaker.speak(art.toString());
                    if (blink == 1) {
                        Log.d("BLINKDBG", "blink received");
                        speaker.speak("Blink Received");
                        speaker.speak(art.getContent());
                    }
                }

                if (blink == 1) {
                    blink = 0;
                    break;
                }
                break;
            }
            LinkedList<Article> reads = feed.getArticlesFeed().get(curKey);
            for (Article read : reads) {
                feedlyClient.markAsReadAPI(read);
            }
            feed.getArticlesFeed().remove(curKey);
            if (feed.getArticlesFeed().size() == 0)
                getArticles();
            speaker.speak("Do you want to continue reading?");

            //wait(200L);
        //}
        //speaker.stop();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
// Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    // This callback is invoked when the Speech Recognizer returns.
    // This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            spokenText = results.get(0);
            // Do something with spokenText
            if (spokenText.equalsIgnoreCase("yes") && inReading == true){
                try {
                    goOn = true;
                    speakBrain();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (spokenText.equalsIgnoreCase("no") && inReading == true){
                goOn = false;
                inReading = false;
            }
            else if (spokenText.equalsIgnoreCase("speak")){
                try {
                    goOn = true;
                    speakBrain();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                displaySpeechRecognizer();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
}
