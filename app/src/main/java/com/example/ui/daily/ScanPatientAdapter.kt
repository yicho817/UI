package com.example.ui.daily

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.ui.R
import android.renderscript.*
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import androidx.cardview.widget.CardView


import com.example.ui.R.color.*
import com.example.ui.daily.data.PatientRecord
import com.google.android.material.color.MaterialColors.getColor


class ScanPatientAdapter(private val recyclerView: RecyclerView,private val patientRecords: List<PatientRecord>) : RecyclerView.Adapter<ScanPatientAdapter.PatientViewHolder>() {
    private val extendedRecords = listOf(patientRecords.last()) + patientRecords + listOf(patientRecords.first()) // 在頭尾各添加最後一筆和第一筆資料
    private var currentIndex = 1
    private val mainHandler = Handler(Looper.getMainLooper())

    // 添加 RenderScript 實例
    private val rs: RenderScript = RenderScript.create(recyclerView.context)
    private val blurScript: ScriptIntrinsicBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))


    class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Declare TextView and RecyclerView references here
        val cv : CardView= itemView.findViewById(R.id.pcard)
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val picture: ImageView = itemView.findViewById(R.id.imageView1)

        val txtLocation: TextView = itemView.findViewById(R.id.txtLocation)
        val txtLastUpdate: TextView = itemView.findViewById(R.id.txtLastUpdate)

    }

    init {
        // 設定 RenderScript
        blurScript.setRadius(25f) // 設定模糊半徑
    }

    override fun getItemCount(): Int {
        return extendedRecords.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.scan_person, parent, false)
        return PatientViewHolder(itemView)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val currentRecord = extendedRecords[position]

        // 在這裡更新卡片的 UI，包括就醫紀錄、用藥紀錄、跌倒紀錄和體徵記錄
        holder.txtName.text = "${currentRecord.userInfo?.name}"
        //currentRecord?.picture?.let {
        //    holder.picture.setImageResource(it)
        //} ?: run {
        //    // 如果 picture 為空值的處理邏輯
        //    // 例如，設定默認的背景資源或執行其他操作
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

        holder.txtLocation.text = "Location:\n${currentRecord?.location}"
        holder.txtLastUpdate.text = "LastUpdate:\n${currentRecord?.lastUpdate}"
        // 其他卡片內容的設置

        // 設置 LinearLayoutManager，這將確保項目按照水平或垂直方向排列
        //val layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)


        scrollToCenter(currentIndex)

        holder.itemView.setOnClickListener {
            // 點擊時切換索引
            currentIndex = (currentIndex) % patientRecords.size + 1
            // 通知 RecyclerView 刷新顯示
            notifyAdapter()
            // 滾動到新的項目，確保它在 RecyclerView 的正中間
            scrollToCenter(currentIndex)
        }

        // 根據索引決定是否隱藏
        //holder.itemView.visibility = if (position == currentIndex) View.VISIBLE else View.GONE

    }


    private fun notifyAdapter() {
        mainHandler.post {
            notifyDataSetChanged()
        }
    }

    private fun scrollToCenter(position: Int) {
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is LinearLayoutManager) {
            val smoothScroller = CenterSmoothScroller(recyclerView.context)
            smoothScroller.targetPosition = position
            layoutManager.startSmoothScroll(smoothScroller)
        }
    }
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        applyBlurToPatientRecords(recyclerView)
    }

    private fun applyBlurToPatientRecords(recyclerView: RecyclerView) {
        for (i in 0 until recyclerView.childCount) {
            val itemView = recyclerView.getChildAt(i)
            applyBlur(itemView)
        }
    }

    private fun applyBlur(view: View) {
        // 在這裡實現模糊處理，這裡使用你之前的模糊邏輯
        val originalBitmap = getBitmapFromView(view)
        val blurredBitmap = Bitmap.createBitmap(originalBitmap)

        val inputAllocation = Allocation.createFromBitmap(rs, originalBitmap)
        val outputAllocation = Allocation.createFromBitmap(rs, blurredBitmap)

        blurScript.setInput(inputAllocation)
        blurScript.forEach(outputAllocation)

        outputAllocation.copyTo(blurredBitmap)

        // 將模糊處理結果應用到整個項目的背景
        view.background = BitmapDrawable(recyclerView.context.resources, blurredBitmap)
    }



    private fun getBitmapFromView(view: View): Bitmap {
        view.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    // 銷毀 RenderScript
    fun destroy() {
        rs.destroy()
    }
}


private class CenterSmoothScroller(context: Context) : LinearSmoothScroller(context) {
    override fun calculateDtToFit(viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int): Int {
        return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2)
    }
}