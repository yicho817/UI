package com.example.ui.daily

import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ui.R

data class Person(
    val name: String,
    val age: Int,
    val height: Double,
    val weight: Double,
    val contact: String,
    val hospital: String,
    val metadata: String,
    val fail:String,
    val sign: String
)


class YourAdapterName(private val personList: List<Person>) : RecyclerView.Adapter<YourAdapterName.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_person, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = personList[position]

        // Bind data to views
        holder.tvName.text = person.name
        holder.tvAge.text = person.age.toString()

        // Set up touch listener
        holder.itemView.setOnTouchListener { _, event ->

            when (event.action) {
                MotionEvent.ACTION_MOVE -> {
                    Log.d("YourAdapterName", "onTouch event: ${event.action}")
                    // Check if the pointer is inside the view
                    if (isPointInsideView(event.rawX, event.rawY, holder.itemView)) {
                        // Mouse hover - expand the card
                        expandCard(holder.detailsLayout)
                        Log.d("YourAdapterName", "onTouch event: ${event.action}")
                    } else {
                        // Mouse exit - collapse the card
                        collapseCard(holder.detailsLayout)
                        Log.d("YourAdapterName", "onTouch event: ${event.action}")
                    }
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_DOWN -> {
                    // Perform a click when touch down is detected
                    holder.itemView.performClick()
                    Log.d("YourAdapterName", "onTouch event: ${event.action}")
                    return@setOnTouchListener true
                }
                else -> return@setOnTouchListener false
            }
        }
    }
    private fun isPointInsideView(x: Float, y: Float, view: View): Boolean {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val rect = Rect(location[0], location[1], location[0] + view.width, location[1] + view.height)
        return rect.contains(x.toInt(), y.toInt())
    }
    override fun getItemCount(): Int {
        return personList.size
    }

    private fun expandCard(view: View) {
        // Expand the card (make it visible)
        view.visibility = View.VISIBLE
    }

    private fun collapseCard(view: View) {
        // Collapse the card (make it gone)
        view.visibility = View.GONE
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.txtName)
        val tvAge: TextView = itemView.findViewById(R.id.txtAge)
        val detailsLayout: LinearLayout = itemView.findViewById(R.id.expandLayout)
    }
}
