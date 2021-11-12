package com.example.toast

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn_showdialog = findViewById<Button>(R.id.btn_ShowDialog)

        btn_showdialog.setOnClickListener {

            var mDialog = AlertDialog.Builder(this)

            mDialog.setTitle("請選擇功能")
            mDialog.setMessage("請根據下方按鈕選擇要顯示的物件")

            mDialog.setNeutralButton("取消") { _, _ ->
                Toast.makeText(
                    this,
                    "Dialog關閉",
                    Toast.LENGTH_SHORT
                ).show()
            }

            mDialog.setNegativeButton("自定義Toast"){ _, _ ->
                showToast()
            }

            mDialog.setPositiveButton("顯示List"){ _, _ ->
                showListDialog()
            }

            mDialog.show()
        }

    }


    private fun showToast() {
        var toast = Toast(this)
        toast.setGravity(Gravity.TOP, 0, 150)
        toast.duration = Toast.LENGTH_SHORT

        var inflater = getLayoutInflater()
        var layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_root))
        toast.view = layout

        toast.show()
    }

    private fun showListDialog(){
        val list = arrayOf("Message1", "Message2", "Message3", "Message4", "Message5")
        val dialog_list = AlertDialog.Builder(this)

        dialog_list.setTitle("使用List呈現")

        dialog_list.setItems(list) {_, i ->
            Toast.makeText(this,  "你選的是" + list[i], Toast.LENGTH_SHORT).show()
        }

        dialog_list.show()
    }
}