package com.mvvm.app.ui.home

import android.app.Application
import android.view.View
import com.mvvm.app.base.BaseNavigator
import com.mvvm.app.base.BaseViewModel
import com.mvvm.app.data.remote.model.devices.MData
import java.util.ArrayList

class DogDetailVM (application: Application) : BaseViewModel<BaseNavigator>(application) {

    fun onClickAction(view: View?) {
        getNavigator().onClickView(view)
    }
    val list = ArrayList<MData>()

    fun addList(images: ArrayList<String>) {
        list.clear()
        images.forEach {
            list.add(MData(
                message = it,
                isSelected = false
            ))
        }
    }
}