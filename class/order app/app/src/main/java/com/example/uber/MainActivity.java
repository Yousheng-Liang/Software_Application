package com.example.uber;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 314){
            if (resultCode == 101){
                Bundle orderBundel = data.getExtras();
                String drink = orderBundel.getString("drink");
                String sugar = orderBundel.getString("sugar");
                String ice = orderBundel.getString("ice");

                TextView tv_order = findViewById(R.id.tv_order);
                tv_order.setText("\n飲料：" + drink + "\n甜度：" + sugar + "\n冰塊：" + ice);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_order = findViewById(R.id.btn_order);

        // Create Intent
        Intent orderIntent = new Intent(this, MainActivity2.class);
        int ORDER_REQUEST = 314;

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(orderIntent, ORDER_REQUEST);
            }
        });

    }
}