package com.example.service_kt

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Thread {
            try {
                Thread.sleep(3000)
                val intent = Intent(this, MainActivity2::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }catch (e: InterruptedException){
                e.printStackTrace()
            }

        }.start()
    }
}