package com.pethoalpar.androidtesstwoocr.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.pethoalpar.androidtesstwoocr.MainApp
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.ToolbarActivity
import com.pethoalpar.androidtesstwoocr.adapter.ItemAdapter
import com.pethoalpar.androidtesstwoocr.room.ItemDao
import kotlinx.android.synthetic.main.activity_show_data.*
import kotlinx.android.synthetic.main.activity_toolbar.*
import org.jetbrains.anko.toast
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class ShowDataActivity : ToolbarActivity() {

    @Inject
    lateinit var itemDao: ItemDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_data)

        MainApp.graph.inject(this)
        initializeToolbar(resources.getString(R.string.somsri_expense))
        useSetting()

        new_item.setOnClickListener {
            startActivity(Intent(this, SelectReceiptFormActivity::class.java))
        }

        btn_setting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }

    }

    private fun setdata() {
        val yVals: ArrayList<BarEntry> = ArrayList()
        val color: MutableList<Int> = ArrayList()
        val listItemName = ArrayList<String>()
        val listItemCost = ArrayList<Double>()


        val item = itemDao.all().sortedBy { it.effectiveDate }

        var x = 0
        var sumTotal = 0.00
        val thisDate = item.first().effectiveDate.split(" ")
        for (i in item) {

            val dateArr = i.effectiveDate.split(" ")
            if (dateArr[1] == thisDate [1] && dateArr[2] == thisDate[2] && dateArr[5] == dateArr[5]) {
            } else {
                listItemName.add("ค่าใช้จ่ายวันที่ $x")
                listItemCost.add(sumTotal)
                yVals.add(BarEntry(x.toFloat(), sumTotal.toFloat()))
                color.add(x, resources.getColor(R.color.chart))
                x++
                sumTotal = 0.00
            }

            sumTotal += i.totalCost

        }

        listItemName.add("ค่าใช้จ่ายวันที่ $x")
        listItemCost.add(sumTotal)
        yVals.add(BarEntry(x.toFloat(), sumTotal.toFloat()))
        color.add(x, resources.getColor(R.color.chart))

        val set = BarDataSet(yVals, null)
        set.colors = color
        set.setDrawValues(true)

        val data = BarData(set)

        chart.data = data
        chart.animateY(500)

        rv_item.layoutManager = LinearLayoutManager(this)
        rv_item.adapter = ItemAdapter(this, listItemName, listItemCost)

    }

    override fun onResume() {
        super.onResume()
        setdata()
        chart.setFitBars(true)
        chart.description = null

    }

}
