package com.feedmybrain.util;

import com.feedmybrain.MainActivity;
import com.feedmybrain.feedlyclient.Article;
import com.feedmybrain.texttospeech.Speaker;

/**
 * Created by yogaub on 17/05/15.
 */
public class SpeakThread extends Thread {



    public SpeakThread (Article article, MainActivity context){
        MyRunnable runnable = new MyRunnable(article, context);
        runnable.run();

    }




}

class MyRunnable implements Runnable{
    private Article article;
    private MainActivity context;
    public MyRunnable(Article article, MainActivity context){
        this.article = article;
        this.context = context;
    }

    @Override
    public void run() {
        Speaker speaker = new Speaker(context);
        speaker.speak(article.toString());
    }
}
