package com.pethoalpar.androidtesstwoocr.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.kaopiz.kprogresshud.KProgressHUD
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.activity.DetailItemsActivity
import com.pethoalpar.androidtesstwoocr.activity.SettingActivity
import com.pethoalpar.androidtesstwoocr.model.Item
import kotlinx.android.synthetic.main.activity_detail_items.view.*
import kotlinx.android.synthetic.main.details_list_item.view.*


class DetailsItemAdapter(private val context: Context, private val items: List<Item>) : RecyclerView.Adapter<DetailsItemViewHolder>() {
    lateinit var loading: KProgressHUD
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsItemViewHolder {
        return DetailsItemViewHolder(LayoutInflater.from(context).inflate(R.layout.details_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: DetailsItemViewHolder, position: Int) {
        val item = items[position]
        holder.btnItem.text = item.detail
        holder.btnCost.text = item.totalCost.toString()

        loading = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Loading")
                .setDimAmount(0.5f)

        holder.lineItemLayout.setOnClickListener {
            loading.show()
            val intent = Intent(context, DetailItemsActivity::class.java)
            intent.putExtra("itemId", item.itemId)
            intent.putExtra("caseItem", "editItem")
            context.startActivity(intent)
            loading.dismiss()
        }
    }

}

class DetailsItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val btnItem = view.tv_details_item!!
    val btnCost = view.detail_item_cost
    val lineItemLayout = view.line_item_layout
}