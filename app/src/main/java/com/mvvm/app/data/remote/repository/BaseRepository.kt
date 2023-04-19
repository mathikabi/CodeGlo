package com.mvvm.app.data.remote.repository

import androidx.lifecycle.MutableLiveData
import com.mvvm.app.data.remote.model.base.*
import com.mvvm.app.utilities.SingleLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

open class BaseRepository {

    private val noNetworkMsg = "No internet connection or network failure"
    private val flagSuccess: String = "SUCCESS"
    private val flagFailure: String = "Failure"

    fun <T> fetch(responseData: MutableLiveData<Resource<T>>,
        requestService: Call<T>?) {
        requestService?.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                val msg = if (t is IOException) noNetworkMsg else t.message!!
                responseData.value = Resource.failure(msg, null)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body() != null) {
                        responseData.value = Resource.success(flagSuccess, response.body())
                    } else {
                        responseData.value = Resource.failure(flagFailure, null)
                    }
                } else if (response.errorBody() != null) {
                    responseData.value = Resource.failure(flagFailure, null)
                }
            }

        })
    }


    /*fun <T> fetch(responseData: MutableLiveData<Resource<T>>,
                  requestService: Call<T>?) {
        requestService?.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                val msg = if (t is IOException) noNetworkMsg else t.message!!
                responseData.value = Resource.failure(msg, null)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body() != null) {
                        responseData.value = Resource.success(flagSuccess, response.body())
                    } else {
                        responseData.value = Resource.failure(flagFailure, null)
                    }
                } else if (response.errorBody() != null) {
                    if (response.code() == 401){
                        loginRequiredData.value = true
                    } else {
                        responseData.value = Resource.failure(flagFailure, null)
                    }

                }
            }

        })
    }*/

}