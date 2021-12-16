package com.example.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.nio.channels.spi.AbstractInterruptibleChannel

class MainActivity : AppCompatActivity() {

    // 建立BroadcastReceiver物件
    private val receiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            // 接收廣播，解析intent
            intent.extras?.let {
                val tv = findViewById<TextView>(R.id.tv)
                tv.text = "${it.getString("msg")}"
            }
        }
    }

    private fun register(channel: String){
        val intentFilter = IntentFilter(channel)
        registerReceiver(receiver, intentFilter)
        val i = Intent(this, MyService::class.java)
        startService(i.putExtra("channel", channel))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnMusic).setOnClickListener(){
            register("music")
        }

        findViewById<Button>(R.id.btnNews).setOnClickListener(){
            register("news")
        }

        findViewById<Button>(R.id.btnSport).setOnClickListener(){
            register("sport")
        }

    }
}