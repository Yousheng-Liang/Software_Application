package com.example.software_application_final_project;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {

    private TreeMap<String, TreeMap<String, HashSet<String>>> map;
    private ArrayList<String> list_date;
    private ArrayList<String> list_number;
    private int itemCount;

    public myAdapter(ArrayList<String> list_date, ArrayList<String> list_number){
        this.list_date = list_date;
        this.list_number = list_number;
        this.itemCount = list_date.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvReceiptDate, tvReceiptNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            tvReceiptDate = itemView.findViewById(R.id.tvReceiptDate);
            tvReceiptNumber = itemView.findViewById(R.id.tvReceiptNumber);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 連結recyclerView布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent ,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 設定各物件要顯示的內容
        try{
            holder.tvReceiptDate.setText(list_date.get(position));
            holder.tvReceiptNumber.setText(list_number.get(position));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list_date.size();
    }


}
