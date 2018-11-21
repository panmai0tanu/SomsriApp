package com.pethoalpar.androidtesstwoocr

import android.app.Application
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pethoalpar.androidtesstwoocr.dagger.AppComponent
import com.pethoalpar.androidtesstwoocr.dagger.AppModule
import com.pethoalpar.androidtesstwoocr.dagger.DaggerAppComponent
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

open class MainApp : Application() {

    companion object {
        @JvmStatic
        lateinit var graph: AppComponent
        lateinit var instance: MainApp
            private set
        var baseUrl = ""
    }

    override fun onCreate() {
        super.onCreate()

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/kanit_light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
        initializeGraph()
    }

    @Suppress("DEPRECATION")
    open fun initializeGraph() {
        graph = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        graph.inject(this)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
