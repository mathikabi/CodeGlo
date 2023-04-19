package com.mvvm.app.data.remote.model.devices


import com.google.gson.annotations.SerializedName

data class DogsListResponse(
    @SerializedName("message")
    var message: List<String>?,

)