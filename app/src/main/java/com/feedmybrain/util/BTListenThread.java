package com.feedmybrain.util;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;

import com.feedmybrain.MainActivity;
import com.feedmybrain.MyBTHandler;
import com.feedmybrain.feedlyclient.Article;
import com.feedmybrain.texttospeech.Speaker;
import com.neurosky.thinkgear.TGDevice;

/**
 * Created by yogaub on 17/05/15.
 */
public class BTListenThread extends Thread {

    private MainActivity context;

    public BTListenThread (MainActivity context){
        this.context = context;
        MyBTRunnable run = new MyBTRunnable(context);
        run.run();

    }



}
class MyBTRunnable implements Runnable{
    private MainActivity context;
    public MyBTRunnable(MainActivity context){
        this.context = context;
    }

    @Override
    public void run() {
        MyBTHandler handler = new MyBTHandler(context);
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if(btAdapter != null) {
            Log.d("BTOOTH", "Bluettoth adapter is NOT null");
            context.tgDevice = new TGDevice(btAdapter, handler);
            context.tgDevice.connect(false);
        }
        else {
            Log.d("BTOOTH","Bluettoth adapter is NULL");
        }
    }
}
