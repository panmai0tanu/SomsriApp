package com.pethoalpar.androidtesstwoocr.dagger

import com.pethoalpar.androidtesstwoocr.LoginActivity
import com.pethoalpar.androidtesstwoocr.MainActivity
import com.pethoalpar.androidtesstwoocr.MainApp
import com.pethoalpar.androidtesstwoocr.ToolbarActivity
import com.pethoalpar.androidtesstwoocr.activity.*
import com.pethoalpar.androidtesstwoocr.adapter.DetailsItemAdapter
import com.pethoalpar.androidtesstwoocr.adapter.ItemAdapter
import com.pethoalpar.androidtesstwoocr.services.Service
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {
    fun inject(mainApp: MainApp)
    fun inject(service: Service)

    fun inject(showDataActivity: ShowDataActivity)
    fun inject(resultActivity: ResultActivity)
    fun inject(selectReceiptFormActivity: SelectReceiptFormActivity)
    fun inject(detailItemsActivity: DetailItemsActivity)
    fun inject(getData7_11Activity: GetData7_11Activity)
    fun inject(getData7Activity: GetData7Activity)
    fun inject(getTotalPriceActivity: GetTotalPriceActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(settingActivity: SettingActivity)
    fun inject(toolbarActivity: ToolbarActivity)
    fun inject(tesseractActivity: TesseractActivity)
    fun inject(tesseractFileProvider: TesseractFileProvider)
    fun inject(loginActivity: LoginActivity)
    fun inject(signUpActivity: SignUpActivity)
    fun inject(itemAdapter: ItemAdapter)
    fun inject(somsriConnectActivity: SomsriConnectActivity)

}