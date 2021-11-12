package com.example.uber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity2 extends AppCompatActivity {

    String sugar = "";
    String ice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        EditText orderbox = findViewById(R.id.orderbox);
        RadioGroup sugarGroup = findViewById(R.id.sugarGroup);
        RadioButton btn_0Sugar = findViewById(R.id.btn_0Sugar);
        RadioButton btn_25Sugar = findViewById(R.id.btn_25Sugar);
        RadioButton btn_50Sugar = findViewById(R.id.btn_50Sugar);
        RadioButton btn_100Sugar = findViewById(R.id.btn_100Sugar);
        RadioGroup iceGroup = findViewById(R.id.iceGroup);
        RadioButton btn_0Ice = findViewById(R.id.btn_0Sugar);
        RadioButton btn_25Ice = findViewById(R.id.btn_25Ice);
        RadioButton btn_100Ice = findViewById(R.id.btn_100Ice);
        Button btnSubmit = findViewById(R.id.btnSubmit);


        sugarGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.btn_0Sugar:
                        sugar = "無糖";
                        break;
                    case R.id.btn_25Sugar:
                        sugar = "少糖";
                        break;
                    case R.id.btn_50Sugar:
                        sugar = "半糖";
                        break;
                    case R.id.btn_100Sugar:
                        sugar = "全糖";
                        break;
                }
            }
        });

        iceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.btn_0Ice:
                        ice = "微冰";
                        break;
                    case R.id.btn_25Ice:
                        ice = "少冰";
                        break;
                    case R.id.btn_100Ice:
                        ice = "正常冰";
                        break;
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle orderBundle = new Bundle();
                orderBundle.putString("drink", orderbox.getText().toString());
                orderBundle.putString("sugar", sugar);
                orderBundle.putString("ice", ice);

                Intent intent = new Intent();
                intent.putExtras(orderBundle);
                setResult(101, intent);

                finish();
            }
        });

    }
}