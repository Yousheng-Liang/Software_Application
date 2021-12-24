package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText boxBookName, boxBookPrice;
    Button btnAdd, btnLookUp, btnDelete, btnEdit;

    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> items;

    private SQLiteDatabase db;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewByIds();

        items = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);


        db = new myDBHelper(this).getWritableDatabase();


        btnLookUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c;
                String bookName = boxBookName.getText().toString();
                if (bookName.length() < 1) {
                    c = db.rawQuery("SELECT * FROM myTable", null);
                } else {
                    c = db.rawQuery("SELECT * FROM myTable WHERE book LIKE \'" + bookName + "\'", null);
                }

                c.moveToFirst();
                items.clear();
                Toast.makeText(getApplication(), "共有" + c.getCount() + "筆資料", Toast.LENGTH_SHORT).show();

                for (int i=0; i < c.getCount(); i++) {
                    items.add("書名：" + c.getString(0) + "\t\t\t\t價格：" + c.getString(1));
                    c.moveToNext();
                }

                adapter.notifyDataSetChanged();
                c.close();
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookname = boxBookName.getText().toString();
                String bookprice = boxBookPrice.getText().toString();
                if (bookname.length() < 1 || bookprice.length() < 1) {
                    Toast.makeText(getApplication(), "欄位請勿留空", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        db.execSQL("INSERT INTO myTable(book, price) VALUES(?,?)",
                                new Object[]{bookname, bookprice});
                        Toast.makeText(getApplication(), "新增書名：" + bookname + "    價格：" + bookprice, Toast.LENGTH_SHORT).show();
                        boxBookPrice.setText("");
                        boxBookName.setText("");
                    }catch (Exception e){
                        Toast.makeText(getApplication(), "新增失敗：" + e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookname = boxBookName.getText().toString();
                String bookprice = boxBookPrice.getText().toString();
                if (bookname.length() < 1 || bookprice.length() < 1) {
                    Toast.makeText(getApplication(), "欄位請勿留空", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        db.execSQL("UPDATE myTable SET price=" + bookprice + " WHERE book LIKE '" + bookname + "'");
                        Toast.makeText(getApplication(), "新增書名：" + bookname + "    價格：" + bookprice, Toast.LENGTH_SHORT).show();
                        boxBookPrice.setText("");
                        boxBookName.setText("");
                    }catch (Exception e){
                        Toast.makeText(getApplication(), "更新失敗：" + e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookname = boxBookName.getText().toString();
                String bookprice = boxBookPrice.getText().toString();
                if (bookname.length() < 1) {
                    Toast.makeText(getApplication(), "欄位請勿留空", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        db.execSQL("DELETE FROM myTable WHERE book LIKE '" + bookname + "'");
                        Toast.makeText(getApplication(), "刪除書名：" + bookname, Toast.LENGTH_SHORT).show();
                        boxBookPrice.setText("");
                        boxBookName.setText("");
                    }catch (Exception e){
                        Toast.makeText(getApplication(), "刪除失敗：" + e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    private void findViewByIds() {
        boxBookName = findViewById(R.id.boxBookName);
        boxBookPrice = findViewById(R.id.boxBookPrice);
        btnAdd = findViewById(R.id.btnAdd);
        btnLookUp = findViewById(R.id.btnLookUp);
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);
        listView = findViewById(R.id.listview);
    }
}