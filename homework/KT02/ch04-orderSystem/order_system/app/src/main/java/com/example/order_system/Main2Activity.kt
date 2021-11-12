package com.example.order_system

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    var sugar = ""
    var ice = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        sugarGroup.setOnCheckedChangeListener { _, i ->
            sugar = sugarGroup.findViewById<RadioButton>(i).text.toString()
        }

        iceGroup.setOnCheckedChangeListener { _, i->
            ice = iceGroup.findViewById<RadioButton>(i).text.toString()
        }

        btnSubmit.setOnClickListener {

            if(orderbox.text.toString().replace(" ", "").isEmpty()){
                Toast.makeText(this, "請輸入飲料名稱", Toast.LENGTH_LONG).show()
            }else{
                var orderBundle = Bundle()
                orderBundle.putString("drink", orderbox.text.toString())
                orderBundle.putString("sugar", sugar)
                orderBundle.putString("ice", ice)

                var intent = Intent()
                intent.putExtras(orderBundle)
                setResult(90, intent)
                finish()
            }

        }
    }
}
