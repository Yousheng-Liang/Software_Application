package com.example.receipt_test;

import java.util.Set;
import java.util.TreeMap;

public class Receipt {
    String receipt_year;
    String receipt_date;
    String receipt_num;

    public Receipt(String receipt_year, String receipt_date, String receipt_num){
        this.receipt_year = receipt_year;
        this.receipt_date = receipt_date;
        this.receipt_num = receipt_num;
    }

    public TreeMap<String, Set<String>> getMap(){
        return null;
    }

}
