package com.example.thread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int val_turtle = 0;
    private int val_rabbit = 0;

    private Button btnStart;
    private SeekBar turtleBar, rabbitBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        turtleBar = findViewById(R.id.turtleBar);
        rabbitBar = findViewById(R.id.rabbitBar);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStart.setEnabled(false);
                val_rabbit = 0;
                val_turtle = 0;
                rabbitBar.setProgress(val_rabbit);
                turtleBar.setProgress(val_turtle);
                runRabbit();
                runTurtle();
            }
        });

    }

    //建立Handler物件接收訊息
    private final Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            //判斷編號，並更新SeekBar的值
            if (msg.what == 1) {
                rabbitBar.setProgress(val_rabbit);
            } else if (msg.what == 2) {
                turtleBar.setProgress(val_turtle);
            }

            //判斷抵達終點
            if (val_rabbit >= 100 && val_turtle < 100) {
                Toast.makeText(MainActivity.this, "兔子勝利", Toast.LENGTH_SHORT).show();
                btnStart.setEnabled(true);
            } else if (val_rabbit < 100 && val_turtle >= 100) {
                Toast.makeText(MainActivity.this, "烏龜勝利", Toast.LENGTH_SHORT).show();
                btnStart.setEnabled(true);
            }
            return false;
        }
    });

    private void runRabbit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //兔子有三分之二的機率偷懶
                boolean sleepProbability[] = {true, true, false};
                while (val_rabbit <= 100 && val_turtle < 100) {
                    try {
                        Thread.sleep(100);
                        if (sleepProbability[(int) (Math.random() * 3)]) {
                            Thread.sleep(300); //兔子偷懶0.3秒
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    val_rabbit += 3; //兔子每次跑三步

                    Message msg = new Message();
                    msg.what = 1; //設定兔子的msg編號為1
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }

    private void runTurtle() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (val_turtle <= 100 && val_rabbit < 100) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    val_turtle += 1; //烏龜每次跑一步

                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }
}