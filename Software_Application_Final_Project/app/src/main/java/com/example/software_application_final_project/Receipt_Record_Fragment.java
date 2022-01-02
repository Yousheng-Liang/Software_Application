package com.example.software_application_final_project;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class Receipt_Record_Fragment extends Fragment {

    // 物件及介面相關變數
    View view;
    Button btnClear;
    private RecyclerView recyclerView;
    myAdapter adapter;

    // 資料庫相關變數
    private final String DB_NAME = "MY_RECEIPT";
    private SQLiteDatabase db;

    // 發票資料相關變數
    ArrayList<String> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_receipt__record, container, false);

        findViewByIds();
        myInit();
        loadDataFromDB();

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "touched", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void loadDataFromDB() {
        db = new myDBHelper(getActivity()).getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DB_NAME + " ORDER BY Receipt_Interval, Receipt_Month", null);
        c.moveToFirst();

        adapter = new myAdapter(c);
        recyclerView.setAdapter(adapter);
    }

    private void findViewByIds(){
        btnClear = view.findViewById(R.id.btnClear);
        recyclerView = view.findViewById(R.id.myRecyclerView);
    }

    private void myInit(){
        // 初始化ArrayList
        list = new ArrayList<>();
        // 設定recyclerview為列表型態
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }
}