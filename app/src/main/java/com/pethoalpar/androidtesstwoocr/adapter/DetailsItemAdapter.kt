package com.pethoalpar.androidtesstwoocr.adapter

import android.content.Context
import android.content.Intent
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.ShowDataActivity
import kotlinx.android.synthetic.main.details_list_item.view.*
import kotlinx.android.synthetic.main.list_item.view.*


class DetailsItemAdapter(private val context: Context, private val items: List<String>) : RecyclerView.Adapter<DetailsItemViewHolder>() {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsItemViewHolder {
        return DetailsItemViewHolder(LayoutInflater.from(context).inflate(R.layout.details_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: DetailsItemViewHolder, position: Int) {
        val item = items[position]
        holder.btnItem.text = item

        holder.btnItem.setOnClickListener {

        }

    }

}

class DetailsItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val btnItem = view.tv_details_item!!
}