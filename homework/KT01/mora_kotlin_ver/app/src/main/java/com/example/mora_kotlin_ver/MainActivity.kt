package com.example.mora_kotlin_ver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val namebox = findViewById<EditText>(R.id.namebox)
        val status = findViewById<TextView>(R.id.textView)
        val btn_s = findViewById<RadioButton>(R.id.rbtn_s)
        val btn_p = findViewById<RadioButton>(R.id.rbtn_p)
        val btn_r = findViewById<RadioButton>(R.id.rbtn_r)
        val btn_start = findViewById<Button>(R.id.btn_start)
        val tv_name = findViewById<TextView>(R.id.tv_name)
        val tv_winner = findViewById<TextView>(R.id.tv_winner)
        val tv_mmora = findViewById<TextView>(R.id.tv_mmora)
        val tv_cmora = findViewById<TextView>(R.id.tv_cmora)

        btn_start.setOnClickListener {
            if(namebox.text.toString().replace(" ", "").length < 1){
                status.setText("請先輸入姓名才能開始遊戲！")
            }else{
                tv_name.setText("名字\n" + namebox.text.toString())

                //電腦出拳
                var computer = (0..2).random() //0:剪刀，1:石頭，2:布
                status.setText(computer.toString())
                if(computer == 0) tv_cmora.setText("電腦出拳\n" + "剪刀")
                else if(computer == 1) tv_cmora.setText("電腦出拳\n" + "石頭")
                else tv_cmora.setText("電腦出拳\n" + "布")

                //玩家出拳
                if(btn_s.isChecked) tv_mmora.setText("玩家出拳\n剪刀")
                else if (btn_r.isChecked) tv_mmora.setText("玩家出拳\n石頭")
                else tv_mmora.setText("玩家出拳\n布")

                //勝負判斷
                if( (btn_s.isChecked && computer==2) || (btn_r.isChecked && computer==0) || (btn_p.isChecked && computer==1) ){
                    status.setText("恭喜你獲勝啦！！")
                    tv_winner.setText("勝利者\n" + namebox.text)
                }else if ( (btn_s.isChecked && computer==0) || (btn_r.isChecked && computer==1) || (btn_p.isChecked && computer==2) ){
                    status.setText("平手 再來一局！")
                    tv_winner.setText("勝利者\n平手")
                }else{
                    status.setText("很遺憾，你輸ㄌ！！")
                    tv_winner.setText("勝利者\n電腦")
                }
            }
        }
    }
}
