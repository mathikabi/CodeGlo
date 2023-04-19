package com.mvvm.app.data.remote.repository

import androidx.lifecycle.MutableLiveData
import com.mvvm.app.data.remote.client.CodeGloClientBuilder
import com.mvvm.app.data.remote.model.base.Resource
import com.mvvm.app.data.remote.model.devices.DogsListResponse
import com.mvvm.app.data.remote.model.vehicle.VehicleLogResponse
import com.mvvm.app.utilities.SingleLiveData

class UsersRepository : BaseRepository() {

    private var apiService = CodeGloClientBuilder.Companion.ServicesApiInterface

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: UsersRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: UsersRepository().also { instance = it }
            }
    }

    fun getDogs(responseData: MutableLiveData<Resource<DogsListResponse>>) {
        fetch(responseData, apiService.fetchManagers()?.getDogsData())
    }

}