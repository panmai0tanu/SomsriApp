package com.pethoalpar.androidtesstwoocr

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.pethoalpar.androidtesstwoocr.adapter.DetailsItemAdapter
import com.pethoalpar.androidtesstwoocr.adapter.ItemAdapter
import kotlinx.android.synthetic.main.activity_show_data.*
import kotlinx.android.synthetic.main.activity_toolbar.*
import java.util.*
import kotlin.collections.ArrayList


class ShowDataActivity : ToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_data)
        initializeToolbar(resources.getString(R.string.somsri_expense))
        useSetting()

        setdata(20)
        chart.setFitBars(true)
        chart.description = null

        new_item.setOnClickListener {
            startActivity(Intent(this, SelectReceiptFormActivity::class.java))
        }

        val listItemName = ArrayList<String>()
        listItemName.add("ค่าใช้จ่ายรวม")
        listItemName.add("ค่าใช้จ่ายรวม")
        listItemName.add("ค่าใช้จ่ายรวม")
        listItemName.add("ค่าใช้จ่่ายรวม")
        listItemName.add("ค่าใช้จ่ายรวม")

        rv_item.layoutManager = LinearLayoutManager(this)
        rv_item.adapter = ItemAdapter(this, listItemName)

        btn_setting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }

    }

    fun IntRange.random() =
            Random().nextInt((endInclusive + 1) - start) +  start

    private fun setdata(count: Int) {
        val yVals: ArrayList<BarEntry> = ArrayList()
        val color: MutableList<Int> = ArrayList()

        for (i in 0 until count) {
            yVals.add(BarEntry(i.toFloat(), (0..100).random().toFloat()))
            color.add(i, resources.getColor(R.color.chart))
        }

        val set = BarDataSet(yVals, null)
        set.colors = color
        set.setDrawValues(true)

        val data = BarData(set)

        chart.data = data
        chart.animateY(500)

    }
}
