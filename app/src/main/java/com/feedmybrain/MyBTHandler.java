package com.feedmybrain;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.neurosky.thinkgear.TGDevice;

/**
 * Created by yogaub on 16/05/15.
 */
public class MyBTHandler extends Handler{

    private MainActivity context;

    public MyBTHandler (MainActivity context){
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        Log.d("BLUETOOTH", "MESSAGE RECIVED");
        Log.d("DEVICEDBG", "" + msg.arg1);
        switch (msg.what) {
            case TGDevice.MSG_STATE_CHANGE:
                switch (msg.arg1) {
                    case TGDevice.STATE_IDLE:
                        break;
                    case TGDevice.STATE_CONNECTING:
                        Log.v("DBGMESSAGE", "Connecting...");
                        Toast.makeText(context, "Connecting...", Toast.LENGTH_LONG).show();
                        break;
                    case TGDevice.STATE_CONNECTED:
                        Log.v("DBGMESSAGE", "Connected");
                        Toast.makeText(context, "CONNECTED", Toast.LENGTH_LONG).show();
                        context.tgDevice.start();
                        break;
                    case TGDevice.STATE_NOT_FOUND:
                        Log.v("DBGMESSAGE", "Can't Find");
                        Toast.makeText(context, "NOT FOUND", Toast.LENGTH_LONG).show();
                        break;
                    case TGDevice.STATE_NOT_PAIRED:
                        Log.v("DBGMESSAGE", "Not Paired");
                        Toast.makeText(context, "NOT PAIRED", Toast.LENGTH_LONG).show();
                        break;
                    case TGDevice.STATE_DISCONNECTED:
                        Log.v("DBGMESSAGE", "Disconnected");
                        Toast.makeText(context, "DISCONNECTED", Toast.LENGTH_LONG).show();
                }

                break;
            case TGDevice.MSG_POOR_SIGNAL:
                //signal = msg.arg1;
                Log.v("DBGMESSAGE", "PoorSignal: " + msg.arg1);
                break;
            case TGDevice.MSG_RAW_DATA:
                //raw1 = msg.arg1;
                //tv.append("Got raw: " + msg.arg1 + "\n");
                break;
            case TGDevice.MSG_HEART_RATE:
                Log.v("DBGMESSAGE", "Heart rate: " + msg.arg1);
                break;
            case TGDevice.MSG_ATTENTION:
                int att = msg.arg1;
                if (att > 80) {
                    //context.speakBrain();
                    context.attention = 1;
                }
                Log.v("DBGMESSAGE", "Attention: " + msg.arg1);
                break;
            case TGDevice.MSG_MEDITATION:

                break;
            case TGDevice.MSG_BLINK:
                if (msg.arg1 > 80) {
                    //context.speakBrain();
                    context.blink = 1;
                }
                Log.v("DBGMESSAGE", "Blink: " + msg.arg1);
                break;
            case TGDevice.MSG_RAW_COUNT:
                //tv.append("Raw Count: " + msg.arg1 + "\n");
                break;
            case TGDevice.MSG_LOW_BATTERY:
                Toast.makeText(context.getApplicationContext(), "Low battery!", Toast.LENGTH_SHORT).show();
                break;
            case TGDevice.MSG_RAW_MULTI:
                //TGRawMulti rawM = (TGRawMulti)msg.obj;
                //tv.append("Raw1: " + rawM.ch1 + "\nRaw2: " + rawM.ch2);
            default:
                break;
        }
    }


}
