package com.pethoalpar.androidtesstwoocr.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.pethoalpar.androidtesstwoocr.MainApp
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.ToolbarActivity
import com.pethoalpar.androidtesstwoocr.adapter.ItemAdapter
import com.pethoalpar.androidtesstwoocr.model.getCurrentDateTime
import com.pethoalpar.androidtesstwoocr.model.getIntDate
import com.pethoalpar.androidtesstwoocr.model.getMonthName
import com.pethoalpar.androidtesstwoocr.room.ItemDao
import kotlinx.android.synthetic.main.activity_show_data.*
import kotlinx.android.synthetic.main.activity_toolbar.*
import java.lang.Math.random
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class ShowDataActivity : ToolbarActivity() {

    @Inject
    lateinit var itemDao: ItemDao

    private var thisMonth = getCurrentDateTime().split("/")[1]
    private var thisYear = getCurrentDateTime().split("/")[2]
    private val originMonth = thisMonth
    private val originYear = thisYear


    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
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

        tv_month_bar.text = "${getMonthName(thisMonth)} ${(thisYear.toInt() + 543)}"

        btn_chev_left.setOnClickListener {

            if (thisMonth == "01") {
                thisYear = (thisYear.toInt() - 1).toString()
                thisMonth = "12"
            } else {
                thisMonth = (thisMonth.toInt() - 1).toString()
                if (thisMonth.count() == 1)
                    thisMonth = "0$thisMonth"
            }
            btn_chev_right.visibility = View.VISIBLE

            setdata()
            chart.setFitBars(true)
            chart.description = null
        }

        btn_chev_right.setOnClickListener {
            if (thisYear == originYear) {
                if (thisMonth.toInt() < originMonth.toInt()) {
                    if (thisMonth == "12") {
                        thisYear = (thisYear.toInt() + 1).toString()
                        thisMonth = "01"
                    } else {
                        thisMonth = (thisMonth.toInt() + 1).toString()
                        if (thisMonth.count() == 1)
                            thisMonth = "0$thisMonth"
                    }
                }

                if (thisMonth == originMonth) btn_chev_right.visibility = View.GONE
            } else if (thisYear.toInt() < originYear.toInt()) {
                if (thisMonth == "12") {
                    thisYear = (thisYear.toInt() + 1).toString()
                    thisMonth = "01"
                } else {
                    thisMonth = (thisMonth.toInt() + 1).toString()
                    if (thisMonth.count() == 1)
                        thisMonth = "0$thisMonth"
                }
            }

            setdata()
            chart.setFitBars(true)
            chart.description = null
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n", "ResourceAsColor")
    private fun setdata() {

        tv_month_bar.text = "${getMonthName(thisMonth)} ${(thisYear.toInt() + 543)}"

        val yVals: ArrayList<BarEntry> = ArrayList()
        val color: MutableList<Int> = ArrayList()
        val listItemCost = ArrayList<Double>()
        val listItemName = ArrayList<String>()

        val chartItem = ArrayList<Double>()

        var z = 0
        val value = (0..100).random()
        while (z < 30){
            yVals.add(BarEntry(z.toFloat(), (0..100).random().toFloat()))
            color.add(z, resources.getColor(R.color.chart))
            z++
        }

        var item = itemDao.all()
        item = item.filter {
            it.effectiveDate.split("/")[1] == thisMonth &&
                    it.effectiveDate.split("/")[2] == thisYear
        }

        var totalCost = 0.00

        if (item.isNotEmpty()) {

            item = item.sortedBy { it.effectiveDate }.reversed()

            var sumTotal = 0.00
            var count = 0
            var thisDate = item.first().effectiveDate
            var dateArr = "00/00/0000"

            for (i in item) {
                totalCost += i.totalCost
                dateArr = i.effectiveDate

                if (dateArr == thisDate) {
                } else {
                    listItemName.add(thisDate)
                    listItemCost.add(sumTotal)
                    thisDate = dateArr

//                    yVals.add(BarEntry(dateArr.split("/")[0].toFloat(), sumTotal.toFloat()))
//                    color.add(dateArr.split("/")[0].toInt(), resources.getColor(R.color.chart))
                    sumTotal = 0.00
                    count++
                }

                sumTotal += i.totalCost

            }

            listItemName.add(thisDate)
            listItemCost.add(sumTotal)

//            yVals.add(BarEntry(dateArr.split("/")[0].toFloat(), sumTotal.toFloat()))
//            color.add(dateArr.split("/")[0].toInt(), resources.getColor(R.color.chart))
            
            chart.run {
                setTouchEnabled(false)
                setDrawBarShadow(false)
            }

            val set = BarDataSet(yVals, null)
            set.colors = color
            set.setDrawValues(true)

            val data = BarData(set)

            chart.data = data
            chart.animateY(500)

            rv_item.layoutManager = LinearLayoutManager(this)
            rv_item.adapter = ItemAdapter(this, listItemName, listItemCost, item)
        } else {
            listItemName.clear()
            listItemCost.clear()

            chart.clear()

            rv_item.layoutManager = LinearLayoutManager(this)
            rv_item.adapter = ItemAdapter(this, listItemName, listItemCost, item)
        }

        tv_total_cost.text = totalCost.toString()
    }

    override fun onResume() {
        super.onResume()
        setdata()
        chart.setFitBars(false)
        chart.description = null

    }

    fun IntRange.random() =
            Random().nextInt((endInclusive + 1) - start) +  start
}
