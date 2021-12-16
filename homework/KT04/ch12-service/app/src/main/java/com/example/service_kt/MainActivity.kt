package com.example.service_kt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btnStartService = findViewById<Button>(R.id.btnStartService)

        btnStartService.setOnClickListener(){
            var intent = Intent(this, MyService::class.java)
            startService(intent)
            Toast.makeText(this, "啟動Service", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}