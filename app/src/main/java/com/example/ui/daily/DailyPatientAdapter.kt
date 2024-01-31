package com.example.ui.daily

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ui.R
import com.example.ui.daily.data.PatientRecord
import com.example.ui.daily.data.UserInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.example.ui.daily.*
import com.example.ui.daily.data.FallRecord
import com.example.ui.daily.data.MedicalRecord
import com.example.ui.daily.data.MedicationRecord
import com.example.ui.daily.data.SymptomRecord

class DailyPatientAdapter(private val patientRecords: List<PatientRecord>) : RecyclerView.Adapter<DailyPatientAdapter.PatientViewHolder>() {

    class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Declare TextView and RecyclerView references here

        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val picture: ImageView = itemView.findViewById(R.id.imageView1)
        val expandLayout: LinearLayout = itemView.findViewById(R.id.expandLayout)
        val collapseLayout: LinearLayout = itemView.findViewById(R.id.collapseLayout)
        val txtLocation: TextView = itemView.findViewById(R.id.txtLocation)
        val txtLastUpdate: TextView = itemView.findViewById(R.id.txtLastUpdate)
        val txtAge: TextView = itemView.findViewById(R.id.txtAge)
        val txtHeight: TextView = itemView.findViewById(R.id.txtHeight)
        val txtWeight: TextView = itemView.findViewById(R.id.txtWeight)
        val txtContact: TextView = itemView.findViewById(R.id.txtContact)
        val recordsLayout: ConstraintLayout = itemView.findViewById(R.id.recordsLayout)
        val rvMedicalRecords: RecyclerView = itemView.findViewById(R.id.rvMedicalRecords)
        val rvMedicationRecords: RecyclerView = itemView.findViewById(R.id.rvMedicationRecords)
        val rvFallRecords: RecyclerView = itemView.findViewById(R.id.rvFallRecords)
        val rvSymptomRecords: RecyclerView = itemView.findViewById(R.id.rvSymptomRecords)
        val back: ImageButton = itemView.findViewById(R.id.back)
        val txtLocation1: TextView = itemView.findViewById(R.id.txtLocation1)
        val txtLastUpdate1: TextView = itemView.findViewById(R.id.txtLastUpdate1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.daily_person, parent, false)
        return PatientViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val currentRecord = patientRecords[position]

        // 在這裡更新卡片的 UI，包括就醫紀錄、用藥紀錄、跌倒紀錄和體徵記錄

        holder.txtName.text = "${currentRecord.userInfo?.name}"
        //currentRecord?.picture?.let {
        //    holder.picture.setImageResource(it)
        //} ?: run {
            // 如果 picture 為空值的處理邏輯
            // 例如，設定默認的背景資源或執行其他操作
        //}
        // 檢查是否有使用者
        //currentRecord.userInfo?.let { users ->
            // 如果使用者列表不為空，則檢查當前位置是否在使用者列表範圍內
        //    if (position < users.size) {
                // 使用對應位置的使用者
        //        val currentUser = users[position]
        //        currentUser.picture?.let { pictureResource ->
        //            holder.picture.setImageResource(pictureResource)
        //        } ?: run {
                    // 如果使用者的圖片為空值的處理邏輯
                    // 例如，設定默認的背景資源或執行其他操作
        //        }
        //    } else {
                // 如果當前位置超出使用者列表範圍的處理邏輯
                // 例如，設定默認的背景資源或執行其他操作
        //    }
        //} ?: run {
            // 如果使用者列表為空的處理邏輯
            // 例如，設定默認的背景資源或執行其他操作
        //}
        //currentRecord.userInfo?.forEach { user ->
            // 顯示對應的照片
        //    user.picture?.let { pictureResource ->
        //        holder.picture.setImageResource(pictureResource)
        //    } ?: run {
                // 如果使用者的圖片為空值的處理邏輯
                // 例如，設定默認的背景資源或執行其他操作
        //    }
        //}

        currentRecord?.userInfo?.let {
            it.picture?.let { pictureResource ->
                holder.picture.setImageResource(pictureResource)
            } ?: run {
                // 如果 userInfo 的圖片為空值的處理邏輯
                // 例如，設定默認的背景資源或執行其他操作
            }
        } ?: run {
            // 如果 userInfo 為空的處理邏輯
            // 例如，設定默認的背景資源或執行其他操作
        }


        holder.txtAge.text = "年紀:\n${currentRecord.userInfo?.age}"
        holder.txtHeight.text = "身高:\n${currentRecord.userInfo?.height }"
        holder.txtWeight.text = "體重:\n${currentRecord.userInfo?.weight}"
        holder.txtContact.text = "聯絡人:\n${currentRecord.userInfo?.contact}"
        holder.txtLocation.text = "Location:\n${currentRecord?.location}"
        holder.txtLastUpdate.text = "Last Update:\n${currentRecord?.lastUpdate}"
        // 其他卡片內容的設置

        // 設置 LinearLayoutManager，這將確保項目按照水平或垂直方向排列
        //val layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)

        // 就醫紀錄
        val medicalRecordAdapter = currentRecord.medicalRecords?.let {
            RecordAdapter(it)
        } ?: RecordAdapter(emptyList())
        holder.rvMedicalRecords.adapter = medicalRecordAdapter
        val medicalLayoutManager =
            LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, true)
        medicalLayoutManager.stackFromEnd = true
        holder.rvMedicalRecords.layoutManager = medicalLayoutManager

        // 用藥紀錄
        val medicationRecordAdapter = currentRecord.medicationRecords?.let {
            RecordAdapter(it)
        } ?: RecordAdapter(emptyList())
        holder.rvMedicationRecords.adapter = medicationRecordAdapter
        val medicationLayoutManager =
            LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, true)
        medicationLayoutManager.stackFromEnd = true
        holder.rvMedicationRecords.layoutManager = medicationLayoutManager

        // 跌倒紀錄
        val fallRecordAdapter =currentRecord.fallRecords?.let {
            RecordAdapter(it)
        } ?: RecordAdapter(emptyList())
        holder.rvFallRecords.adapter = fallRecordAdapter
        val fallLayoutManager =
            LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, true)
        fallLayoutManager.stackFromEnd = true
        holder.rvFallRecords.layoutManager = fallLayoutManager

        // 體徵記錄
        val symptomRecordAdapter = currentRecord.symptomRecords?.let {
            RecordAdapter(it)
        } ?: RecordAdapter(emptyList())
        holder.rvSymptomRecords.adapter = symptomRecordAdapter
        val symptomLayoutManager =
            LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, true)
        symptomLayoutManager.stackFromEnd = true
        holder.rvSymptomRecords.layoutManager = symptomLayoutManager

        holder.txtLocation1.text = "Location:\n${currentRecord?.location}"
        holder.txtLastUpdate1.text = "Last Update:\n${currentRecord?.lastUpdate}"

        holder.itemView.setOnClickListener {

            if (holder.collapseLayout.visibility == View.GONE) {
                expandCard(holder.collapseLayout)
                collapseCard(holder.expandLayout)
                collapseCard(holder.recordsLayout)
            } else{
                collapseCard(holder.collapseLayout)
                expandCard(holder.expandLayout)
                expandCard(holder.recordsLayout)
            }
        }

        holder.back.setOnClickListener {
            expandCard(holder.collapseLayout)
            collapseCard(holder.expandLayout)
            collapseCard(holder.recordsLayout)
        }

    }
    override fun getItemCount(): Int {
        return patientRecords.size
    }
    private fun expandCard(view: View) {
        // Expand the card (make it visible)
        view.visibility = View.VISIBLE
    }

    private fun collapseCard(view: View) {
        // Collapse the card (make it gone)
        view.visibility = View.GONE
    }


}


