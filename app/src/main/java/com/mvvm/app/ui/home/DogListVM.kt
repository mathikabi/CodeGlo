package com.mvvm.app.ui.home

import android.app.Application
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.mvvm.app.base.BaseNavigator
import com.mvvm.app.base.BaseViewModel
import com.mvvm.app.data.remote.model.base.Resource
import com.mvvm.app.data.remote.model.devices.DogsListResponse
import com.mvvm.app.data.remote.model.devices.MData
import com.mvvm.app.data.remote.repository.UsersRepository
import com.mvvm.app.utilities.SingleLiveData

class DogListVM (application: Application) : BaseViewModel<BaseNavigator>(application) {

    var dogceoRes =  SingleLiveData<Resource<DogsListResponse>>()
    val list = ArrayList<MData>()

    var selectImageList = ArrayList<String>()
    var btnVisible = ObservableBoolean(false)

    fun onClickAction(view: View?) {
        getNavigator().onClickView(view)
    }

    init {
        dogceoApi()
    }

    private fun dogceoApi() {
        UsersRepository.getInstance().getDogs(responseData = dogceoRes)
    }

    fun getMessageList(message: List<String>): List<MData> {
        message.forEach {
            list.add(MData(message = it))
        }
        return  list
    }

    fun updatedMessage(position: Int): List<MData> {
        val isSelected = list[position].isSelected ?: false
        list[position].isSelected = !isSelected
        return  list
    }
}