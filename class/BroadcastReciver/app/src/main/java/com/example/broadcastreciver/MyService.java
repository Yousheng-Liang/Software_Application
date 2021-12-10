package com.example.broadcastreciver;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    static Boolean flag = false;
    int h=0, m=0, s=0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        flag = intent.getBooleanExtra("flag", false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(flag){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    s++;
                    if(s>=60){
                        s=0;
                        m++;
                        if(m>=60){
                            m=0;
                            h++;
                        }
                    }

                    Intent intent = new Intent("myMsg");
                    Bundle bundle = new Bundle();
                    bundle.putInt("H", h);
                    bundle.putInt("M", m);
                    bundle.putInt("S", s);
                    intent.putExtras(bundle);
                    sendBroadcast(intent);
                }

            }
        }).start();

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}