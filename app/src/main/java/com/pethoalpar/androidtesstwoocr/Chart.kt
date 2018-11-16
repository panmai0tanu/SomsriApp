package com.pethoalpar.androidtesstwoocr

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.activity_chart.*


class Chart : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        setdata(30)
        chart.setFitBars(true)
        chart.description = null
    }

    private fun setdata(count: Int) {
        val yVals: ArrayList<BarEntry> = ArrayList()
        val color: MutableList<Int> = ArrayList()

        for (i in 0 until count) {
            yVals.add(BarEntry(i.toFloat(), i.toFloat()))
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
