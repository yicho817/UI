package com.example.ui.daily

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView  // Import TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ui.R
import com.example.ui.daily.*
import com.example.ui.daily.data.FallRecord
import com.example.ui.daily.data.MedicalRecord
import com.example.ui.daily.data.MedicationRecord
import com.example.ui.daily.data.SymptomRecord

class RecordAdapter(private val records: List<Any>) : RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

    // 為每個項目使用 ViewBinding
    class RecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtDate: TextView = itemView.findViewById(R.id.txtDate)
        val txtDescription: TextView = itemView.findViewById(R.id.txtDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.record_item, parent, false)
        return RecordViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val currentRecord = records[position]

        // 使用綁定對象訪問視圖
        holder.txtDate.text = when (currentRecord) {
            is MedicalRecord -> currentRecord.date.toString()
            is MedicationRecord -> currentRecord.date.toString()
            is FallRecord -> currentRecord.date.toString()
            is SymptomRecord -> currentRecord.date.toString()
            else -> ""
        }

        holder.txtDescription.text = when (currentRecord) {
            is MedicalRecord -> currentRecord.medical
            is MedicationRecord -> currentRecord.medication
            is FallRecord -> currentRecord.location
            is SymptomRecord -> currentRecord.symptoms
            else -> ""
        }
    }

    override fun getItemCount(): Int {
        return records.size
    }
}
