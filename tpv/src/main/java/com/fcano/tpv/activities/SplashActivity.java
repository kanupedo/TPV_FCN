package com.fcano.tpv.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ListView;

import com.fcano.tpv.R;
import com.fcano.tpv.utils.JSON_Manager;

/**
 * Created by Fernando on 10/05/2014.
 */
public class SplashActivity extends Activity {
    private Thread mSplashThread;
    private JSON_Manager json_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        final SplashActivity sPlashScreen = this;
        ListView listView = (ListView) findViewById(R.id.list_item);
        //json_manager = new JSON_Manager(listView, this);

        mSplashThread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(2000);
                    }
                } catch (InterruptedException ex) {
                }

                finish();

                Intent intent = new Intent();
                intent.setClass(sPlashScreen, MainActivity.class);
                startActivity(intent);

            }
        };

        mSplashThread.start();
    }


    @Override

    public boolean onTouchEvent(MotionEvent evt) {
        if (evt.getAction() == MotionEvent.ACTION_DOWN) {
            synchronized (mSplashThread) {
                mSplashThread.notifyAll();
            }
        }
        return true;
    }
}
