package com.pethoalpar.androidtesstwoocr.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.pethoalpar.androidtesstwoocr.R
import kotlinx.android.synthetic.main.list_item.view.*


class ItemAdapter(private val context: Context, private val items: List<String>) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.tvItemName.text = item

        val listItemName = ArrayList<String>()
        listItemName.add("รายการซื้อของที่เซเว่น")
        listItemName.add("รายการซื้อของที่เซเว่น")
        listItemName.add("รายการซื้อของที่เซเว่น")

        holder.rvDetailItem.layoutManager = LinearLayoutManager(context)
        holder.rvDetailItem.adapter = DetailsItemAdapter(context, listItemName)

        holder.btnItem.setOnClickListener {
            if (holder.layourDetailsItem.visibility == View.GONE) {
                holder.layourDetailsItem.visibility = View.VISIBLE
                holder.angleRight.setBackgroundResource(R.drawable.angle_up_green)
            } else {
                holder.layourDetailsItem.visibility = View.GONE
                holder.angleRight.setBackgroundResource(R.drawable.angle_down_green)
            }
        }

    }

}

class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val btnItem = view.item_layout!!
    val tvItemName = view.tv_item!!
    val rvDetailItem = view.rv_details_item!!
    val layourDetailsItem = view.details_item_layout!!
    val angleRight = view.iv_angle!!
}