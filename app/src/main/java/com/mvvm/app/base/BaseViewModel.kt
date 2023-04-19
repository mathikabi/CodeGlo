package com.mvvm.app.base

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import com.mvvm.app.utilities.NetworkUtils
import com.mvvm.app.utilities.SingleLiveData
import java.text.NumberFormat
import java.util.*

abstract class BaseViewModel<N>(application: Application) : AndroidViewModel(application) {

    val mApplication: Application = application
    private var mNavigator: N? = null
    val alertMsg = SingleLiveData<String>()
    val resource: Resources = mApplication.resources

    fun isNetworkConnected(): Boolean {
        val network = NetworkUtils.isNetworkConnected(mApplication)
        if (!network) showToast("No Network, Please check internet connection.")
        return network
    }

    fun showToast(msg: String) {
       // mApplication.toast(msg)
    }

    fun getNavigator(): N {
        return mNavigator!!
    }

    fun setNavigator(navigator: N) {
        this.mNavigator = navigator
    }

    fun showQuickAlert(msg: String){
        alertMsg.value = msg
    }

    fun getCount(isPlus: Boolean, count: Int): Int {
        return if (isPlus) {
            count + 1
        } else {
            val minus = count - 1
            if (minus >= 0) minus else 0
        }
    }

    fun getCountService(isPlus: Boolean, count: Int,MaxQuantity:Int): Int {
        return if (isPlus) {
            if(count==MaxQuantity){
                //showToast("Max Quantity Reached")
                return count
            }else {
                count + 1
            }
        } else {
            val minus = count - 1
            if (minus >= 0) minus else 0
        }
    }

    fun getStringToInt(str: String?) : Int {
        return if (!str.isNullOrEmpty()) {
            str.toInt()
        } else {
            0
        }
    }

    fun numberFormatPriceInt(price: Int?): String {
        return if (price != null) {
            val number = NumberFormat.getInstance(Locale.ENGLISH)
            number.minimumFractionDigits = 0
            number.maximumFractionDigits = 0
            number.format(price)
        } else {
            "0"
        }
    }

    fun numberFormatPriceInt(price: Double?): String {
        return if (price != null) {
            val number = NumberFormat.getInstance(Locale.ENGLISH)
            number.minimumFractionDigits = 0
            number.maximumFractionDigits = 0
            number.format(price)
        } else {
            "0"
        }
    }

    fun numberFormatPriceInt(price: Float?): String {
        return if (price != null) {
            val number = NumberFormat.getInstance(Locale.ENGLISH)
            number.minimumFractionDigits = 0
            number.maximumFractionDigits = 0
            number.format(price)
        } else {
            "0"
        }
    }

    fun getPriceWithoutCurrency(price: Int?): String {
        return if (price != null){
            if (price >= 0) numberFormatPriceInt(price) else "0"
        } else {
            "0"
        }
    }

    fun getPriceWithoutCurrency(price: Float?): String {
        return if (price != null){
            if (price >= 0) numberFormatPriceInt(price) else "0"
        } else {
            "0"
        }
    }

    fun getPriceWithoutCurrency(price: Double?): String {
        return if (price != null){
            if (price >= 0) numberFormatPriceInt(price) else "0"
        } else {
            "0"
        }
    }
}