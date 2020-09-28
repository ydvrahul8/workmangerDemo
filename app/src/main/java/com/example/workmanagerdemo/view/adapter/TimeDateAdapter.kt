package com.example.workmanagerdemo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workmanagerdemo.R
import com.example.workmanagerdemo.model.DateTimeData

class TimeDateAdapter : RecyclerView.Adapter<TimeDateAdapter.MyViewHolder>() {

    private var items = ArrayList<DateTimeData>()

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text = view.findViewById<TextView>(R.id.textView_dateTime)
        fun bindTo(dateTimeData: DateTimeData) {
            text.text = dateTimeData.dateTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(list: List<DateTimeData>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (items!!.isNotEmpty())
            holder.bindTo(items[position])
    }
}