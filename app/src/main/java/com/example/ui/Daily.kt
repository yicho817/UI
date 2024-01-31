package com.example.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ui.daily.*
import com.example.ui.daily.data.UserRepository
import com.example.ui.daily.data.initRecords


class Daily: AppCompatActivity() {
    private lateinit var userRepository: UserRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily)

        // 將 toolbar_layout.xml 包含進來
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        layoutInflater.inflate(R.layout.toolbar_layout, toolbar, true)

        val btnHome = toolbar.findViewById<ImageButton>(R.id.btnHome)
        btnHome.visibility = View.VISIBLE

        btnHome.setOnClickListener {
            Log.d("ButtonClick", "Home button clicked")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        //userRepository = UserRepository(filesDir.absolutePath + "/user_records.csv")
        //val patientRecords = userRepository.readData()


        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        // Set up RecyclerView and Adapter with horizontal layout
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        // 如果 personList.size 不是 3 的倍數，補充到 3 的倍數
        //while (patientRecords.size % 6 != 0) {
        //patientRecords.add(
        //PatientRecord(),
        //// 添加空数据
        //)
        //}

        val adapter = DailyPatientAdapter(initRecords)
        recyclerView.adapter = adapter

    }


}

