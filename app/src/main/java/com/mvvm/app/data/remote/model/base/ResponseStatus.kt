package com.mvvm.app.data.remote.model.base

import com.google.gson.annotations.SerializedName

data class ResponseStatus (
    @SerializedName("ResponseCode") val responseCode: String,
    @SerializedName("ResponseFlag") val responseFlag: String,
    @SerializedName("ResponseMessage") val responseMessage: String,
    @SerializedName("ResponseId") val responseID: Any? = null
)