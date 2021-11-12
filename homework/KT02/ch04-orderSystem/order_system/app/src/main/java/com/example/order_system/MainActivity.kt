package com.example.order_system

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 314){
            if(resultCode == 90){
                var drink = data?.extras?.getString("drink") //取得bundle內key為drink的字串資料
                var ice = data?.extras?.getString("ice")     //取得bundle內key為ice的字串資料
                var sugar = data?.extras?.getString("sugar") //取得bundle內key為sugar的字串資料

                tv_order.text = "\n\n" + drink + "\n" + sugar + "\n" + ice  //將點餐結果顯示出來
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_order.setOnClickListener {
            //宣告intent，從目前的Activity指向Main2Activity
            var intent = Intent(this, Main2Activity::class.java)
            startActivityForResult(intent, 314) //要求傳回值及設定requestCode
        }
    }
}
