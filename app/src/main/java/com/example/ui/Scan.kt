package com.example.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ui.daily.*
import com.example.ui.daily.ScanPatientAdapter
import com.example.ui.daily.data.UserRepository
import com.example.ui.daily.data.initRecords


class Scan: AppCompatActivity() {
    private lateinit var userRepository: UserRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)


        // 將 toolbar_layout.xml 包含進來
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        layoutInflater.inflate(R.layout.toolbar_layout, toolbar, true)
        val button1 = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        // 設定工具列的元件
        val btnHome = toolbar.findViewById<ImageButton>(R.id.btnHome)
        btnHome.visibility = View.VISIBLE

        btnHome.setOnClickListener {
            Log.d("ButtonClick", "Home button clicked")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        // Set up RecyclerView and Adapter with horizontal layout
        //val layoutManager = LinearLayoutManager(this)
        val recyclerView: RecyclerView = findViewById(/* id = */ R.id.recyclerView)


        // 如果 personList.size 不是 3 的倍數，補充到 3 的倍數
        //while (patientRecords.size % 6 != 0) {
        //    patientRecords.add(
        //        PatientRecord(),
        //    // 添加空数据
        //   )
        //}
        //userRepository = UserRepository(filesDir.absolutePath + "/user_records.csv")
        //val patientRecords = userRepository.readData()

        val adapter = ScanPatientAdapter(recyclerView,initRecords)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        // 設置 ItemDecoration，這裡的 80 是半透明的 alpha 值，你可以根據需要調整
        //recyclerView.addItemDecoration(CustomItemDecoration(128))



        val sc:ConstraintLayout = findViewById(R.id.sc)
        sc.visibility = View.VISIBLE
        val sc1:ConstraintLayout = findViewById(R.id.sc1)
        val sc12 :ConstraintLayout = findViewById(R.id.sc12)

        button1.setOnClickListener {
            sc.visibility = View.GONE
            sc1.visibility = View.VISIBLE
            sc12.visibility = View.GONE
        }

        button2.setOnClickListener {
        }



        button3.setOnClickListener {
            sc.visibility = View.GONE
            sc1.visibility = View.GONE
            sc12.visibility = View.VISIBLE
        }


        button4.setOnClickListener {
            sc.visibility = View.VISIBLE
            sc1.visibility = View.GONE
            sc12.visibility = View.GONE
        }

        button5.setOnClickListener {
            sc.visibility = View.GONE
            sc1.visibility = View.VISIBLE
            sc12.visibility = View.GONE
        }
    }

}

