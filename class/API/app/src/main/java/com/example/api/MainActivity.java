package com.example.api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String target_url = "https://lab12-api.web.app/";
        Button btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request request = new Request.Builder().url(target_url).build();

                new OkHttpClient().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.e("查詢失敗", e.toString());
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(response.code() == 200){
                            if(response.body() == null) return;
                            myData data = new Gson().fromJson(response.body().string(), myData.class);
                            final String[] items = new String[data.result.results.length];

                            for(int i=0; i<items.length; i++){
                                items[i] = "\n列車即將進入：" + data.result.results[i].Station + "\n列車行駛目的地：" + data.result.results[i].Destination;

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new AlertDialog.Builder(MainActivity.this)
                                                .setTitle("台北捷運列車到站站名")
                                                .setItems(items, null)
                                                .show();
                                    }
                                });
                            }
                        }else if(!response.isSuccessful()){
                            Log.e("伺服器錯誤", response.code() + " " + response.message());
                        }else{
                            Log.e("伺服器錯誤", response.code() + " " + response.message());                        }
                    }
                });
            }
        });

    }

    class myData{
        Result result;
        class Result{
            Results[] results;
            class Results{
                String Station;
                String Destination;
            }
        }
    }
}