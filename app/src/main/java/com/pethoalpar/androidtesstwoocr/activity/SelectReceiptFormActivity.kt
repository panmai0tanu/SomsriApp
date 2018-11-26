package com.pethoalpar.androidtesstwoocr.activity

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.kaopiz.kprogresshud.KProgressHUD
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.pethoalpar.androidtesstwoocr.MainApp
import com.pethoalpar.androidtesstwoocr.R
import com.pethoalpar.androidtesstwoocr.ToolbarActivity
import com.pethoalpar.androidtesstwoocr.room.ItemDao
import kotlinx.android.synthetic.main.activity_select_receipt_form.*

class SelectReceiptFormActivity : ToolbarActivity() {

    private lateinit var loading: KProgressHUD
    lateinit var itemDao: ItemDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_receipt_form)
        MainApp.graph.inject(this)
        initializeToolbar()
        useBack()

        val edit = intent.getBooleanExtra("edit", false)

        loading = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.loading))
                .setDimAmount(0.5f)

        validatePermission()

        val intent = Intent(this, TesseractActivity::class.java)
        intent.putExtra("languageProcess", "eng")
        iv_sevenEleven.setOnClickListener {
            startActivity(intent)
            loading.show()
        }

        iv_other.setOnClickListener {
            Log.d("PANMAI", itemDao.all().toString())
        }

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

    override fun onResume() {
        super.onResume()
        loading.dismiss()
    }
}
