package com.vp.threadqueue;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {


    private static String DEBUG_TAG = MainActivity.class.getSimpleName();
    private Handler mHandler;
    private ThreadWithoutLooper mThreadWithoutLooper;
    private ThreadWithtLooper mThreadWithtLooper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  mThreadWithoutLooper = new ThreadWithoutLooper();
        mThreadWithoutLooper.start();*/

        mThreadWithtLooper = new ThreadWithtLooper();
        mThreadWithtLooper.start();

        ((Button)findViewById(R.id.button_thread)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.sendEmptyMessage(1);

            }
        });

        ((Button)findViewById(R.id.button_threadlooper)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.sendEmptyMessage(2);
            }
        });
    }


    class ThreadWithoutLooper extends  Thread{

        @Override
        public void run() {
            super.run();

            mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what){
                        case 1:
                            Log.d(DEBUG_TAG, "NO LOOPER ATTACHED 1 "+msg.what);
                            break;
                        case 2:
                            Log.d(DEBUG_TAG, "NO LOOPER ATTACHED 2 "+msg.what);

                            break;
                    }
                }
            };

        }
    }

    class ThreadWithtLooper extends  Thread{

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what){
                        case 1:
                            Log.d(DEBUG_TAG, "LOOPER ATTACHED 1 "+msg.what);
                            break;
                        case 2:
                            Log.d(DEBUG_TAG, "LOOPER ATTACHED 2 "+msg.what);

                            break;
                    }
                }
            };
            MessageQueue messageQueue = Looper.myQueue();
            Log.d(DEBUG_TAG , ""+messageQueue);
            Looper.loop();

        }
    }

}
