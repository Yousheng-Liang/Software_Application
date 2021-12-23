package com.example.software_application_final_project;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
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

    private RecyclerView recyclerView;
    myAdapter adapter;

    View view;
    Button btnClear;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String RECEIPT_DATA = "RECEIPT_DATA";
    ArrayList<TreeMap<String, TreeMap<String, HashSet<String>>>> list;
    TreeMap<String, TreeMap<String, HashSet<String>>> map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_receipt__record, container, false);


        myInit();
        findViewByIds();
        readDataFromSharedPreference();
        showList();


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();
                readDataFromSharedPreference();
                showList();
            }
        });

        return view;
    }

    private void showList() {

        ArrayList<String> list_date = new ArrayList<>();
        ArrayList<String> list_number = new ArrayList<>();

        // 將map轉成list再塞給adapter(因為需要用到list的index及getItemCount方法 recyclerView才有作用)
        for(Map.Entry<String, ?> itor_of_receipt_year : map.entrySet()){
            // 利用迭代抓出每年的map
            String year = (String) itor_of_receipt_year.getKey();
            TreeMap<String, HashSet<String>> little_map = (TreeMap<String, HashSet<String>>) itor_of_receipt_year.getValue();
            // 再利用迭代抓出某年的所有資料
            for(Map.Entry<String, ?> itor_of_receipt_data : little_map.entrySet()){
                String date = (String) itor_of_receipt_data.getKey();
                HashSet<String> number = little_map.get(date);
                // 將資料塞入list中
                // 如果同一天有超過一張發票，將set拆開，recyclerview才能顯示
                if(number.size()>1){
                    for (String s: number){
                        list_date.add(date);
                        list_number.add(s);
                    }
                }else{
                    list_date.add(date);
                    String tmp = String.valueOf(number);
                    list_number.add(tmp.substring(1, tmp.length()-1));
                }
            }
        }

        // 設置RecyclerView為列表型態
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 設置格線
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        // 將資料交給adapter
        adapter = new myAdapter(list_date, list_number);

        // 設置adapter給recycler_view
        recyclerView.setAdapter(adapter);
    }

    private void readDataFromSharedPreference(){
        Map<String, ?> tmpMap = sharedPreferences.getAll();
        if(tmpMap == null) return;
        String data = "";
        String JsonData = "";
        for( Map.Entry<String, ?> elements : tmpMap.entrySet()){
            String key = elements.getKey();
            Object val = elements.getValue();
            data = key + ": " + val + "\n";
            JsonData = (String) val;
        }

        if (JsonData.length() <= 0) return;

        if(JsonData.charAt(0) == '['){
            JsonData = JsonData.substring(1, JsonData.length()-1);
        }

        try {
            map = new Gson().fromJson(JsonData, new TypeToken<TreeMap<String, TreeMap<String, HashSet<String>>>>(){}.getType());
            list.add(map);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void findViewByIds(){
        btnClear = view.findViewById(R.id.btnClear);
        recyclerView = view.findViewById(R.id.myRecyclerView);
    }

    private void myInit(){
        sharedPreferences = getActivity().getSharedPreferences(RECEIPT_DATA, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        map = new TreeMap<>();
        list = new ArrayList<>();
    }
}