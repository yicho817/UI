package com.example.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class Scan1: AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan1)

    }
    override fun onStart (){
        super.onStart()

        // 將 toolbar_layout.xml 包含進來
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        layoutInflater.inflate(R.layout.toolbar_layout, toolbar, true)

        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        // 設定工具列的元件
        val btnHome = toolbar.findViewById<ImageButton>(R.id.btnHome)
        btnHome.visibility = View.VISIBLE

        btnHome.setOnClickListener {
            Log.d("ButtonClick", "Home button clicked")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        button4.setOnClickListener {
            onBackPressed() // 模拟后退操作
        }

    }

}

