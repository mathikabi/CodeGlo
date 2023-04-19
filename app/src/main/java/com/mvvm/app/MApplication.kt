package com.mvvm.app

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import androidx.databinding.DataBindingUtil
import com.facebook.stetho.Stetho
import com.gatebot.app.BuildConfig
import com.mvvm.app.component.BaseDataBindingComponent
import java.text.NumberFormat


class MApplication : Application(){

    companion object {
        private var sInstance: MApplication? = null

        fun getInstance(): MApplication? {
            return sInstance
        }
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onCreate() {
        super.onCreate()
        sInstance = this

        NumberFormat.getInstance().maximumFractionDigits = 2
        DataBindingUtil.setDefaultComponent(BaseDataBindingComponent())
        val appInfo: ApplicationInfo
        try {
            if (BuildConfig.DEBUG) {
                Stetho.initializeWithDefaults(this)
            }
            appInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            val bundle = appInfo.metaData
            val apiType = bundle.getString("apiType") ?: "production"
        } catch (e: PackageManager.NameNotFoundException) {
            // ignored
        }
    }
}
