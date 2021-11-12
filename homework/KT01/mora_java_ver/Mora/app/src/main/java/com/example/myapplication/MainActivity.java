package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText namebox = findViewById(R.id.namebox);
        TextView tv_name = findViewById(R.id.tv_name);
        TextView tv_winner = findViewById(R.id.tv_winner);
        TextView tv_mmora = findViewById(R.id.tv_mmora);
        TextView tv_cmora = findViewById(R.id.tv_cmora);
        TextView status = findViewById(R.id.textView);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton btn_scissor = findViewById(R.id.rbtn_s);
        RadioButton btn_rock = findViewById(R.id.rbtn_r);
        RadioButton btn_paper = findViewById(R.id.rbtn_p);
        Button btn_game = findViewById(R.id.btn_start);

        //監聽按鈕事件
        btn_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //確認玩家姓名
                if(namebox.getText().toString().replace(" ", "").length() < 1){
                    status.setText("請先輸入姓名才能開始遊戲！");
                }else{
                    tv_name.setText("名字\n"+namebox.getText());
                    int computer = (int)(Math.random()*3); //0:剪刀，1:石頭，2:布
                    //電腦出拳
                    if (computer == 0){
                        tv_cmora.setText("電腦出拳\n剪刀");
                    }else if (computer == 1){
                        tv_cmora.setText("電腦出拳\n石頭");
                    }else{
                        tv_cmora.setText("電腦出拳\n布");
                    }
                    //判斷我方出拳
                    if(btn_scissor.isChecked()){
                        tv_mmora.setText("我方出拳\n剪刀");
                    }else if(btn_paper.isChecked()){
                        tv_mmora.setText("我方出拳\n石頭");
                    }else{
                        tv_mmora.setText("我方出拳\n布");
                    }
                    //判斷獲勝者
                    if( (btn_scissor.isChecked() && computer==2) ||
                            (btn_rock.isChecked() && computer==0) ||
                            (btn_paper.isChecked() && computer==1) ){
                        status.setText("恭喜你獲勝啦！！");
                        tv_winner.setText("勝利者\n"+namebox.getText());
                    }else if( (btn_scissor.isChecked() && computer==1) ||
                            (btn_rock.isChecked() && computer==2) ||
                            (btn_paper.isChecked() && computer==0)){
                        status.setText("很遺憾，你輸ㄌ");
                        tv_winner.setText("勝利者\n電腦");
                    }else{
                        tv_winner.setText("勝利者\n平手");
                        status.setText("平手 再來一局！");
                    }
                }

            }
        });
    }
}