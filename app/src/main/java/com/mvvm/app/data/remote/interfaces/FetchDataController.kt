package com.mvvm.app.data.remote.interfaces

import com.mvvm.app.data.remote.model.devices.DogsListResponse
import retrofit2.Call
import retrofit2.http.GET

interface FetchDataController {

    @GET("/api/breed/hound/images/")
    fun getDogsData(): Call<DogsListResponse>



}