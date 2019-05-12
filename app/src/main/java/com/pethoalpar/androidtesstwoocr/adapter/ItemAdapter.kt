package com.pethoalpar.androidtesstwoocr.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.pethoalpar.androidtesstwoocr.MainApp
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.model.Item
import com.pethoalpar.androidtesstwoocr.model.getCurrentDateTime
import com.pethoalpar.androidtesstwoocr.room.ItemDao
import kotlinx.android.synthetic.main.list_item.view.*
import javax.inject.Inject


class ItemAdapter(private val context: Context, private val titleNames: ArrayList<String>, private val costs: ArrayList<Double>, private val item: List<Item>) : RecyclerView.Adapter<ItemViewHolder>() {

    @Inject
    lateinit var itemDao: ItemDao

    override fun getItemCount(): Int {
        return titleNames.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        MainApp.graph.inject(this)
        return ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val date = titleNames[position]
        val cost = costs[position]
        holder.totalCostOfDay.text = cost.toString()

        if (getCurrentDateTime() == date)
            holder.dateName.text = "วันนี้"
        else
            holder.dateName.text = date

        val itemList = item.filter { it.effectiveDate == date }

        holder.rvDetailItem.layoutManager = LinearLayoutManager(context)
        holder.rvDetailItem.adapter = DetailsItemAdapter(context, itemList)

        holder.btnItem.setOnClickListener {
            if (holder.layourDetailsItem.visibility == View.GONE) {
                holder.layourDetailsItem.visibility = View.VISIBLE
                holder.angleRight.setBackgroundResource(R.drawable.angle_up_green)
            } else {
                holder.layourDetailsItem.visibility = View.GONE
                holder.angleRight.setBackgroundResource(R.drawable.angle_down_green)
            }
        }

        if (position == 0) {
            holder.layourDetailsItem.visibility = View.VISIBLE
            holder.angleRight.setBackgroundResource(R.drawable.angle_up_green)
        }

    }

}

class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val btnItem = view.item_layout!!
    val rvDetailItem = view.rv_details_item!!
    val layourDetailsItem = view.details_item_layout!!
    val angleRight = view.iv_angle!!
    val totalCostOfDay = view.tv_total_cost_of_day
    val dateName = view.tv_date
}