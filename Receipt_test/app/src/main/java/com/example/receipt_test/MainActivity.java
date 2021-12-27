package com.example.receipt_test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    // 物件變數宣告
    EditText boxYear, boxDate, boxNumber;
    Button btnWrite, btnRead, btnClear;
    TextView tvContent;

    // 儲存發票資料之相關變數
    TreeMap<String, HashSet<String>> map; // 內層的map(日期)
    TreeMap<String, TreeMap<String, HashSet<String>>> map2; // 外層的map(年分)

    HashSet<String> receipt_set;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // 變數宣告
    String receipt_year, receipt_num, receipt_date;
    String RECEIPT_DATA = "RECEIPT_DATA";

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewByIds();
        myInit();

        tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clipboardManager.setText(tvContent.getText().toString());
                Toast.makeText(getApplication(), "文字已複製至剪貼簿", Toast.LENGTH_SHORT).show();
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receipt_year = boxYear.getText().toString();
                receipt_date = boxDate.getText().toString();
                receipt_num = boxNumber.getText().toString();

                map = map2.get(receipt_year);
                if(map == null){
                    map = new TreeMap<>();
                }else{

                }

                // 如果還沒儲存過這天的發票
                if(!map.containsKey(receipt_date)){
                    // 就直接新增一個然後塞入map中
                    HashSet<String> tmp_set = new HashSet<>();
                    tmp_set.add(receipt_num);
                    map.put(receipt_date, tmp_set);
                }else {  // 如果儲存過的話
                    // 先撈出原本的set資料
                    HashSet<String> tmp_set = map.get(receipt_date);
                    // 再將新的資料放入set後塞回map
                    tmp_set.add(receipt_num);
                    map.put(receipt_date, tmp_set);
                }

                // 將map的值轉為Json後儲存至SharedPreference
//                ArrayList<TreeMap<String, HashSet<String>>> list = new ArrayList<>();
//                list.add(map);
                map2.put(receipt_year, map);
                ArrayList<TreeMap<String, TreeMap<String, HashSet<String>>>> list = new ArrayList<>();
                list.add(map2);
                String jsonData = new Gson().toJson(list);
                jsonData = jsonData.substring(1, jsonData.length()-1); // 去掉頭尾的中括號
                tvContent.setText(jsonData);
                editor.putString(RECEIPT_DATA, jsonData);
                editor.commit();
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, ?> testMap = sharedPreferences.getAll();
                String data = "";
                for(Map.Entry<String, ?> element : testMap.entrySet()) {
                    String key = element.getKey();
                    Object val = element.getValue();
                    data = key + ": " + val + "\n";
                }
                tvContent.setText(data);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                tvContent.setText("未儲存任何資料");
            }
        });

    }

    private void myInit(){
        // 初始化map
        map = new TreeMap<>();
        map2 = new TreeMap<>();
        sharedPreferences = getSharedPreferences(RECEIPT_DATA, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        // 讀取儲存的Json資料並放入map中
        String data = sharedPreferences.getString(RECEIPT_DATA, "[{}]");
        Log.d("LYS", "init data before = " + data);
        if(data.charAt(0) == '[')
            data = data.substring(1, data.length()-1);
        Log.d("LYS", "init data = " + data);
        map2 = new Gson().fromJson(data, new TypeToken<TreeMap<String,TreeMap<String, Set<String>>>>(){}.getType());
    }

    private void findViewByIds(){
        boxYear = findViewById(R.id.boxYear);
        boxDate = findViewById(R.id.boxDate);
        boxNumber = findViewById(R.id.boxNumber);
        btnClear = findViewById(R.id.btnClear);
        btnWrite = findViewById(R.id.btnWrite);
        btnRead = findViewById(R.id.btnRead);
        tvContent = findViewById(R.id.tvData);
    }
}