package com.pethoalpar.androidtesstwoocr

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.activity_chart.*




class Chart : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        chart

        var dataObjects = arrayListOf(1,2,3)

        val entries: MutableList<PieEntry> = ArrayList()

        for (data in dataObjects) {
            var entry = PieEntry(data.toFloat())
            entries.add(entry)
        }

        val dataSet: PieDataSet = PieDataSet(entries, "label") //new LineDataSet(list1, "Label")
        dataSet.color = R.color.abc_btn_colored_borderless_text_material
        dataSet.valueTextColor = R.color.blue

        val lineData = PieData(dataSet)
        chart.data = lineData
        chart.invalidate() // refresh
    }
}
