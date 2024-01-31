package com.example.ui.daily

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.renderscript.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ui.R

class BlurredRecyclerViewAdapter(private val context: Context, private val data: List<String>) :
    RecyclerView.Adapter<BlurredRecyclerViewAdapter.ViewHolder>() {

    private var blurredBitmaps: List<Bitmap> = listOf()

    init {
        initializeBlurredBitmaps()
    }

    private fun initializeBlurredBitmaps() {
        val rs = RenderScript.create(context)
        val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))

        blurredBitmaps = data.map {
            val originalBitmap = getBitmapFromView(createItemView(it))
            val blurredBitmap = Bitmap.createBitmap(originalBitmap)

            val inputAllocation = Allocation.createFromBitmap(rs, originalBitmap)
            val outputAllocation = Allocation.createFromBitmap(rs, blurredBitmap)

            blurScript.setRadius(25f) // 設定模糊半徑
            blurScript.setInput(inputAllocation)
            blurScript.forEach(outputAllocation)

            outputAllocation.copyTo(blurredBitmap)
            blurredBitmap
        }

        rs.destroy()
    }

    private fun createItemView(text: String): View {
        val itemView = LayoutInflater.from(context).inflate(R.layout.scan_person, null)
        val textView = itemView.findViewById<TextView>(R.id.txtName)
        textView.text = text
        return itemView
    }

    private fun getBitmapFromView(view: View): Bitmap {
        view.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.scan_person, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.background = BitmapDrawable(context.resources, blurredBitmaps[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
