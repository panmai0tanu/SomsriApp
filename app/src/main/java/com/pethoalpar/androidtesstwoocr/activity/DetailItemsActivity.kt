package com.pethoalpar.androidtesstwoocr.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kaopiz.kprogresshud.KProgressHUD
import com.pethoalpar.androidtesstwoocr.MainApp
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.R.color.white
import com.pethoalpar.androidtesstwoocr.ToolbarActivity
import com.pethoalpar.androidtesstwoocr.room.DetailItemDao
import com.pethoalpar.androidtesstwoocr.room.ItemDao
import com.pethoalpar.androidtesstwoocr.room.LineItemDao
import kotlinx.android.synthetic.main.activity_detail_items.*
import org.jetbrains.anko.toast
import java.util.*
import javax.inject.Inject
import android.content.Intent
import com.pethoalpar.androidtesstwoocr.MainActivity
import android.provider.MediaStore
import android.media.MediaScannerConnection
import android.os.Environment
import java.nio.file.Files.exists
import android.os.Environment.getExternalStorageDirectory
import android.widget.ImageView
import com.pethoalpar.androidtesstwoocr.model.*
import com.pethoalpar.androidtesstwoocr.room.UserDao
import com.pethoalpar.androidtesstwoocr.services.Service
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class DetailItemsActivity : ToolbarActivity() {

    @Inject
    lateinit var itemDao: ItemDao
    @Inject
    lateinit var userDao: UserDao
    @Inject
    lateinit var service: Service
    @Inject
    lateinit var lineItemDao: LineItemDao

    @Inject
    lateinit var detailItemDao: DetailItemDao
    private var imgFile:String = ""
    private lateinit var itemFind:List<Item>
    lateinit var loading: KProgressHUD
    private val GALLERY = 1
    private val CAMERA = 2
    private var urlNewImage: String = ""
    private val IMAGE_DIRECTORY = "/demonuts"
    private var checkType = "income"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_items)
        MainApp.graph.inject(this)
        initializeToolbar("รายการซื้อของที่เซเว่น")
        useBack()

        loading = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.loading))
                .setDimAmount(0.5f)

        val totalCostString = intent.getStringExtra("totalCost")
        if (!intent.getStringExtra("imgFile").isNullOrEmpty()){
            imgFile = intent.getStringExtra("imgFile")
        }
        val caseItem = intent.getStringExtra("caseItem")
        val receiptNumber = intent.getStringExtra("receiptNumber")
        val itemId = intent.getIntExtra("itemId", 0)
        val totalCost = totalCostString
        var date = intent.getStringExtra("date")
        date = date.replace('-', '/')
        date = date.replace('.', '/')

        var createNewItem = true

        if (caseItem == "editItem"){
            itemFind = itemDao.findByItemId(itemId)
            et_reciept_id.setText(itemFind[0].receiptNumber)
            et_date.setText(itemFind[0].effectiveDate)
            et_detail.setText(itemFind[0].detail)
            et_total_cost.setText(itemFind[0].totalCost.toString())
            imgFile = itemFind[0].imgUrlFileName
            if (itemFind[0].itemType == "expense"){
                tv_checkbox_income_check.visibility = View.GONE
                tv_checkbox_income_not_check.visibility = View.VISIBLE
                tv_checkbox_expense_check.visibility = View.VISIBLE
                tv_checkbox_expense_not_check.visibility = View.GONE
            }
            createNewItem = false

            if (userDao.all().isNotEmpty()) {
                tv_share.visibility = View.VISIBLE
            } else{
                tv_share.visibility = View.GONE
            }
            tv_share.setOnClickListener {
                if (userDao.all().isNotEmpty()) {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("ส่งข้อมูล: " + et_detail.text)
                    builder.setIcon(R.drawable.warning_icon)

                    builder.setNegativeButton("ยกเลิก") { _, which ->

                    }
                    builder.setPositiveButton("ตกลง") { _, which ->
                        loading.show()
                        service.sendData(userDao.all().first().id, itemFind[0].itemId, itemFind[0].detail,itemFind[0].totalCost, itemFind[0].effectiveDate)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    Log.d("PANMAI2", "success!! : " + it.detail)
                                    toast("ส่งข้อมูลเรียบร้อยแล้ว")

                                }, { error ->
                                    Log.d("PANMAI", "ERROR: " + error.message)
                                })
                        loading.show()
                        finish()
                    }

                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                } else {
                    toast("กรุณาเชื่อมต่อกับสมศรี")
                }
            }
        }
        else if(caseItem == "createNew") {
            et_reciept_id.setText(receiptNumber)
            et_date.setText(date)
            et_total_cost.setText(totalCost.toString())
        }


        if (imgFile != "") {
            val bitmap = loadBitmap(imgFile)
            iv_receipt.setImageBitmap(bitmap)
        }

        btn_save_data.setOnClickListener {

//            if (et_total_cost.text.toString())

            val item = constructorItem()
            item.itemId = (0..1000).random()
            while (itemDao.findByItemId(item.itemId!!).isNotEmpty())
                item.itemId = (0..1000).random()

            if (createNewItem)
                createItem(item)
            else
                updateItem(itemFind[0])

            val lineItem = constructorLineItem()
            lineItem.itemId = item.itemId
            lineItem.lineItemId = (0..1000).random()
            while (lineItemDao.findByLineItemId(lineItem.lineItemId!!).isNotEmpty())
                lineItem.lineItemId = (0..1000).random()

            //add amount and cost line item

            val detailItem = constructorDetailItem()
            detailItem.lineItemId = lineItem.lineItemId
            detailItem.detailItemId = (0..1000).random()
            while (detailItemDao.findByDetailItemId(detailItem.detailItemId!!).isNotEmpty())
                detailItem.detailItemId = (0..1000).random()

        }

        btn_delete_data.setOnClickListener {
            if (caseItem == "editItem"){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Delete")
                builder.setIcon(R.drawable.warning_icon)

                builder.setNegativeButton("ยกเลิก"){_, which ->

                }
                builder.setPositiveButton("ตกลง"){_, which ->
                    deleteItem(itemFind[0])
                    finish()
                }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            } else {
                toast("ไม่มีรายการ")
            }
        }

        iv_angle.setOnClickListener {
            if (detail_item.visibility == View.GONE) {
                detail_item.visibility = View.VISIBLE
                iv_angle.setBackgroundResource(R.drawable.angle_up_green)
            } else {
                detail_item.visibility = View.GONE
                iv_angle.setBackgroundResource(R.drawable.angle_down_green)
            }
        }

        tv_checkbox_expense_not_check.setOnClickListener {
            tv_checkbox_expense_not_check.visibility = View.GONE
            tv_checkbox_expense_check.visibility = View.VISIBLE
            checkType = "expense"
            tv_checkbox_income_not_check.visibility = View.VISIBLE
            tv_checkbox_income_check.visibility = View.GONE
        }

        tv_checkbox_income_not_check.setOnClickListener {
            tv_checkbox_income_not_check.visibility = View.GONE
            tv_checkbox_income_check.visibility = View.VISIBLE
            checkType = "income"
            tv_checkbox_expense_not_check.visibility = View.VISIBLE
            tv_checkbox_expense_check.visibility = View.GONE
        }

//        iv_edit.setOnClickListener {
//            takePhotoFromCamera()
//        }

//        var checkDelete = false
//        var newCost: String = ""
//        for (i in et_total_cost.text) {
//            if (i == '.') {
//                checkDelete = true
//            }
//
//            if (!checkDelete)
//                newCost += i
//        }
//
//        et_total_cost.setText(newCost)

    }

    fun validateSaveData(){
        if (et_reciept_id.text.toString() != ""){}
        if (et_total_cost.text.toString() != ""){}

    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    urlNewImage = saveImage(bitmap)

                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        } else if (requestCode == CAMERA) {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            urlNewImage = saveImage(thumbnail)
            val bitmap = loadBitmap(urlNewImage)
            iv_receipt.setImageBitmap(bitmap)
        }
    }

    fun saveImage(myBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 10, bytes)
        val wallpaperDirectory = File(
                Environment.getExternalStorageDirectory().toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }

        try {
            val f = File(wallpaperDirectory, Calendar.getInstance()
                    .timeInMillis.toString() + ".jpg")
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                    arrayOf(f.getPath()),
                    arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath())

            return f.getAbsolutePath()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    override fun onPause() {
        super.onPause()
        loading!!.dismiss()
    }

    private fun loadBitmap(filePath: String): Bitmap {
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        return BitmapFactory.decodeFile(filePath, options)
    }

    private fun intOrString(str: String): Double {
        val v = str.toDoubleOrNull()
        return when (v) {
            null -> 0.00
            else -> v
        }
    }

    private fun createItem(item: Item) {

        et_reciept_id.setBackgroundResource(R.color.white)
        et_date.setBackgroundResource(R.color.white)
        et_detail.setBackgroundResource(R.color.white)
        et_total_cost.setBackgroundResource(R.color.white)

        if (et_reciept_id.text.toString() == "") {
            toast("กรุณาตรวจสอบข้อมูล!!")
            et_reciept_id.setBackgroundResource(R.color.red)
        } else if (et_detail.text.toString() == "") {
            toast("กรุณาตรวจสอบข้อมูล!!")
            et_detail.setBackgroundResource(R.color.red)
        } else if (et_date.text.toString() == "") {
            toast("กรุณาตรวจสอบข้อมูล!!")
            et_date.setBackgroundResource(R.color.red)
        } else {
            val checkTotalCost = et_total_cost.text.toString().split(".")
            var checkValidate = false
            for (i in checkTotalCost){
                if (i.toIntOrNull() == null){
                    et_total_cost.setBackgroundResource(R.color.red)
                    checkValidate = true
                }
            }

            if (!checkValidate) {
                item.receiptNumber = et_reciept_id.text.toString()
                item.totalCost = et_total_cost.text.toString().toDouble()
                item.detail = et_detail.text.toString()
                item.effectiveDate = et_date.text.toString()
                item.imgUrlFileName = imgFile
                if (checkType == "income") {
                    item.itemType = "income"
                } else {
                    item.itemType = "expense"
                }

                itemDao.create(item)
                finish()
            }
        }


    }

    private fun updateItem(item: Item) {
        item.receiptNumber = et_reciept_id.text.toString()
        item.totalCost = et_total_cost.text.toString().toDouble()
        item.detail = et_detail.text.toString()
        item.effectiveDate = et_date.text.toString()
        item.imgUrlFileName = imgFile
        itemDao.update(item)
    }

    private fun deleteItem(item: Item){
        itemDao.delete(item)
    }

    fun IntRange.random() =
            Random().nextInt((endInclusive + 1) - start) + start
}
