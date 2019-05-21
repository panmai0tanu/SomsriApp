package com.pethoalpar.androidtesstwoocr.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.pethoalpar.androidtesstwoocr.MainApp
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.ToolbarActivity
import com.pethoalpar.androidtesstwoocr.adapter.ItemAdapter
import com.pethoalpar.androidtesstwoocr.model.*
import com.pethoalpar.androidtesstwoocr.room.ItemDao
import kotlinx.android.synthetic.main.activity_show_data.*
import kotlinx.android.synthetic.main.activity_toolbar.*
import kotlinx.android.synthetic.main.dialog_welcome.view.*
import org.jetbrains.anko.makeCall
import org.jetbrains.anko.toast
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
    private var selectShow = "Day"
    private var checkedItem = 0

    protected val deleteCheck = false


    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_data)

        MainApp.graph.inject(this)
        initializeToolbar(resources.getString(R.string.somsri_expense))
        useSetting()

        ///////// edit ////////
        if (itemDao.all().isEmpty()) {
            dialogWelcome()
        }
        validatePermission()

        iv_checkbox_expense_check.setOnClickListener {
            iv_checkbox_expense_check.visibility = View.GONE
            iv_checkbox_expense_no.visibility = View.VISIBLE
            if (iv_checkbox_income_check.visibility == View.VISIBLE) {
                tv_show_sum_total.text = "รายรับรวมทั้งหมดของเดือนนี้"
            } else {
                tv_show_sum_total.text = "ค่าใช้จ่ายทั้งหมดของเดือนนี้"
            }
            setdata()
        }

        iv_checkbox_expense_no.setOnClickListener {
            iv_checkbox_expense_no.visibility = View.GONE
            iv_checkbox_expense_check.visibility = View.VISIBLE
            if (iv_checkbox_income_check.visibility == View.GONE) {
                tv_show_sum_total.text = "รายจ่ายรวมทั้งหมดของเดือนนี้"
            } else {
                tv_show_sum_total.text = "ค่าใช้จ่ายทั้งหมดของเดือนนี้"
            }
            setdata()
        }

        iv_checkbox_income_check.setOnClickListener {
            iv_checkbox_income_check.visibility = View.GONE
            iv_checkbox_income_no.visibility = View.VISIBLE
            if (iv_checkbox_expense_check.visibility == View.VISIBLE) {
                tv_show_sum_total.text = "รายจ่ายรวมทั้งหมดของเดือนนี้"
            } else {
                tv_show_sum_total.text = "ค่าใช้จ่ายทั้งหมดของเดือนนี้"
            }
            setdata()
        }

        iv_checkbox_income_no.setOnClickListener {
            iv_checkbox_income_no.visibility = View.GONE
            iv_checkbox_income_check.visibility = View.VISIBLE
            if (iv_checkbox_expense_check.visibility == View.GONE) {
                tv_show_sum_total.text = "รายรับรวมทั้งหมดของเดือนนี้"
            } else {
                tv_show_sum_total.text = "ค่าใช้จ่ายทั้งหมดของเดือนนี้"
            }
            setdata()
        }

        new_item.setOnClickListener {
            startActivity(Intent(this, SelectReceiptFormActivity::class.java))
        }

        btn_setting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }

        if (selectShow == "Day")
            tv_month_bar.text = "${getMonthName(thisMonth)} ${(thisYear)}"
        else
            tv_month_bar.text = "ปี พ.ศ. ${(thisYear)}"

        btn_chev_left.setOnClickListener {
            if (selectShow == "Day") {
                if (thisMonth == "01") {
                    thisYear = (thisYear.toInt() - 1).toString()
                    thisMonth = "12"
                } else {
                    thisMonth = (thisMonth.toInt() - 1).toString()
                    if (thisMonth.count() == 1)
                        thisMonth = "0$thisMonth"
                }
            } else {
                thisYear = (thisYear.toInt() - 1).toString()
            }
            btn_chev_right.visibility = View.VISIBLE

            setdata()
            chart.setFitBars(true)
            chart.description = null
        }

        btn_chev_right.setOnClickListener {
            if (selectShow == "Day") {
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
            } else {
                thisYear = (thisYear.toInt() + 1).toString()
                if (thisYear == originYear) btn_chev_right.visibility = View.GONE
            }

            setdata()
            chart.setFitBars(true)
            chart.description = null
        }

        iv_select.setOnClickListener {
            val options: Array<String> = arrayOf("Day", "Month")
            lateinit var dialog: AlertDialog
            val builder = AlertDialog.Builder(this)
            builder.setTitle("เลือกรูปแบบการแสดงผล")
            builder.setSingleChoiceItems(options, checkedItem) { _, which ->
                if (selectShow != options[which]) {
                    checkedItem = which
                    selectShow = options[which]
                    setdata()
                }
                dialog.dismiss()
            }

            dialog = builder.create()
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n", "ResourceAsColor")
    private fun setdata() {

        if (selectShow == "Day")
            tv_month_bar.text = "${getMonthName(thisMonth)} ${(thisYear.toInt())}"
        else
            tv_month_bar.text = "ปี พ.ศ. ${thisYear.toInt()}"

        val yVals: ArrayList<BarEntry> = ArrayList()
        val color: MutableList<Int> = ArrayList()
        val listItemCost = ArrayList<Double>()
        val listItemName = ArrayList<String>()
        var allSumTotal = 0.00

        var itemTypeExpense = ""
        if (iv_checkbox_expense_check.visibility == View.VISIBLE) {
            itemTypeExpense = "expense"
        }
        var itemTypeIncome = ""
        if (iv_checkbox_income_check.visibility == View.VISIBLE) {
            itemTypeIncome = "income"
        }

        var item = itemDao.all()
        if (selectShow == "Day") {
            item = item.filter {
                it.effectiveDate.split("/")[1] == thisMonth &&
                        it.effectiveDate.split("/")[2] == thisYear &&
                        it.itemType == itemTypeExpense
            } + item.filter {
                it.effectiveDate.split("/")[1] == thisMonth &&
                        it.effectiveDate.split("/")[2] == thisYear &&
                        it.itemType == itemTypeIncome
            }
        } else {
            item = item.filter {
                it.effectiveDate.split("/")[2] == thisYear && it.itemType == itemTypeExpense
            } + item.filter {
                it.effectiveDate.split("/")[2] == thisYear && it.itemType == itemTypeIncome

            }
        }

        val sumTotalArr: ArrayList<Float> = ArrayList()
        val allDateArr: ArrayList<String> = ArrayList()
        if (item.isNotEmpty()) {

            yVals.add(BarEntry(0.toFloat(), 0.toFloat()))
            if (selectShow == "Day") {
                if (thisMonth.toInt() == 1 || thisMonth.toInt() == 3 || thisMonth.toInt() == 5 || thisMonth.toInt() == 7 ||
                        thisMonth.toInt() == 8 || thisMonth.toInt() == 10 || thisMonth.toInt() == 12) {
                    yVals.add(BarEntry(32.toFloat(), 0.toFloat()))
                } else if (thisMonth.toInt() == 2) {
                    yVals.add(BarEntry(30.toFloat(), 0.toFloat()))
                } else {
                    yVals.add(BarEntry(31.toFloat(), 0.toFloat()))
                }
            } else {
                yVals.add(BarEntry(13.toFloat(), 0.toFloat()))
            }

            item = item.sortedBy {
                it.effectiveDate.split("/")[2] + it.effectiveDate.split("/")[1] +
                        it.effectiveDate.split("/")[0]
            }.reversed()

            if (selectShow == "Day") {
                var sumTotalExpense = 0.00
                var sumTotalIncome = 0.00
                var thisDate = item.first().effectiveDate
                var dateArr = "00/00/0000"
                var sumTotal = 0.00

                for (i in item) {
                    dateArr = i.effectiveDate
                    if (dateArr == thisDate) {
                        if (i.itemType == "expense") {
                            sumTotalExpense += i.totalCost
                        } else {
                            sumTotalIncome += i.totalCost
                        }
                    } else {

                        sumTotal = sumTotalIncome - sumTotalExpense
                        sumTotalArr.add(sumTotal.toFloat())
                        allDateArr.add(thisDate)
                        allSumTotal += sumTotal
                        listItemName.add(thisDate)
                        listItemCost.add(sumTotal)

                        thisDate = dateArr
                        sumTotalExpense = 0.00
                        sumTotalIncome = 0.00

                        if (i.itemType == "expense") {
                            sumTotalExpense += i.totalCost
                        } else {
                            sumTotalIncome += i.totalCost
                        }
                    }
                }

                sumTotal = sumTotalIncome - sumTotalExpense
                allSumTotal += sumTotal
                sumTotalArr.add(sumTotal.toFloat())
                allDateArr.add(thisDate)
                listItemName.add(thisDate)
                listItemCost.add(sumTotal)

                for ((index, totalPrice) in sumTotalArr.reversed().withIndex()) {
                    if (totalPrice > 0) {
                        yVals.add(BarEntry(allDateArr[sumTotalArr.size - index - 1].split("/")[0].toFloat(), totalPrice))
                        color.add(index, resources.getColor(R.color.chart))
                    } else {
                        yVals.add(BarEntry(allDateArr[sumTotalArr.size - index - 1].split("/")[0].toFloat(), totalPrice * -1))
                        color.add(index, resources.getColor(R.color.red))
                    }
                }
            } else {

                var sumTotalExpense = 0.00
                var sumTotalIncome = 0.00
                var thisDate = item.first().effectiveDate.split("/")[1] + "/" + item.first().effectiveDate.split("/")[2]
                var dateArr = "00/0000"
                var sumTotal = 0.00

                for (i in item) {
                    dateArr = i.effectiveDate.split("/")[1] + "/" + i.effectiveDate.split("/")[2]
                    if (dateArr == thisDate) {
                        if (i.itemType == "expense") {
                            sumTotalExpense += i.totalCost
                        } else {
                            sumTotalIncome += i.totalCost
                        }
                    } else {
                        sumTotal = sumTotalIncome - sumTotalExpense
                        sumTotalArr.add(sumTotal.toFloat())
                        allDateArr.add(thisDate)
                        allSumTotal += sumTotal
                        listItemName.add(thisDate)
                        listItemCost.add(sumTotal)

                        thisDate = dateArr
                        sumTotalExpense = 0.00
                        sumTotalIncome = 0.00

                        if (i.itemType == "expense") {
                            sumTotalExpense += i.totalCost
                        } else {
                            sumTotalIncome += i.totalCost
                        }
                    }
                }

                sumTotal = sumTotalIncome - sumTotalExpense
                allSumTotal += sumTotal
                sumTotalArr.add(sumTotal.toFloat())
                allDateArr.add(thisDate)
                listItemName.add(thisDate)
                listItemCost.add(sumTotal)

                for ((index, totalPrice) in sumTotalArr.reversed().withIndex()) {
                    if (totalPrice > 0) {
                        yVals.add(BarEntry(allDateArr[sumTotalArr.size - index - 1].split("/")[0].toFloat(), totalPrice))
                        color.add(index, resources.getColor(R.color.chart))
                    } else {
                        yVals.add(BarEntry(allDateArr[sumTotalArr.size - index - 1].split("/")[0].toFloat(), totalPrice * -1))
                        color.add(index, resources.getColor(R.color.red))
                    }
                }

            }

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
            rv_item.adapter = ItemAdapter(this, listItemName, listItemCost, item, selectShow)
        } else {
            listItemName.clear()
            listItemCost.clear()

            rv_item.layoutManager = LinearLayoutManager(this)
            rv_item.adapter = ItemAdapter(this, listItemName, listItemCost, item, selectShow)

            chart.clear()
        }

        tv_total_cost.text = allSumTotal.toString()

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        setdata()
        chart.setFitBars(false)
        chart.description = null

    }

    private fun validatePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Dexter.withActivity(this)
                    .withPermissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ).withListener(object : MultiplePermissionsListener {
                        override fun onPermissionsChecked(report: MultiplePermissionsReport) {/* ... */
                        }

                        override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {/* ... */
                        }

                    }).check()

        }
    }

    fun dialogWelcome() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_welcome, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
        //show dialog
        val mAlertDialog = mBuilder.show()
        //login button click of custom layout
        mDialogView.tv_skip.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
        }
        //cancel button click of custom layout
        mDialogView.tv_start.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
        }
    }

    fun IntRange.random() =
            Random().nextInt((endInclusive + 1) - start) + start

    fun IntRange.random2() =
            Random().nextInt((endInclusive + 1) - start) + start
}
