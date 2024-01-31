package com.example.ui

import CustomLayoutManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onStart (){
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
        try {
            // 將 toolbar_layout.xml 包含進來
            val toolbar = findViewById<Toolbar>(R.id.toolbar)

            layoutInflater.inflate(R.layout.toolbar_layout, toolbar, true)

            // 設定工具列的元件
            val btnHome = toolbar.findViewById<ImageButton>(R.id.btnHome)
            btnHome.visibility = View.GONE

            btnHome.setOnClickListener {
                Log.d("ButtonClick", "Home button clicked")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }



            val button1 = findViewById<ImageButton>(R.id.imageButton)
            val button2 = findViewById<ImageButton>(R.id.imageButton2)
            val button3 = findViewById<ImageButton>(R.id.imageButton3)
            val button4 = findViewById<ImageButton>(R.id.imageButton4)
            val button5 = findViewById<ImageButton>(R.id.imageButton5)

            button1.setOnClickListener {
                val intent = Intent("com.example.ui.ACTION_VIEW_HEALTH_DAILY")
                startActivity(intent)
                finish()
            }
            button2.setOnClickListener {
                val intent = Intent("com.example.ui.ACTION_VIEW_HEALTH_SCAN")
                startActivity(intent)
                finish()
            }


        } catch (e: Exception) {
            Log.e("MainActivity", "An error occurred: ${e.message}")
        }
    }
}
