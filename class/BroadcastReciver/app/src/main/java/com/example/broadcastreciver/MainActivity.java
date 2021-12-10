package com.example.broadcastreciver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Boolean flag = false;
    private TextView tv;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            tv.setText(String.format("%02d:%02d:%02d", bundle.getInt("H"), bundle.getInt("M"), bundle.getInt("S")));
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
        Button btnStart = findViewById(R.id.btnStart);

        registerReceiver(receiver, new IntentFilter("myMsg"));

        flag = MyService.flag;
        if(flag)
            btnStart.setText("暫停");
        else
            btnStart.setText("開始");

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = !flag;
                if (flag){
                    btnStart.setText("暫停");
                    Toast.makeText(MainActivity.this, "計時開始", Toast.LENGTH_SHORT).show();
                }else{
                    btnStart.setText("開始");
                    Toast.makeText(MainActivity.this, "計時暫停", Toast.LENGTH_SHORT).show();
                }
                startService(new Intent(MainActivity.this, MyService.class).putExtra("flag", flag));
            }
        });
    }
}