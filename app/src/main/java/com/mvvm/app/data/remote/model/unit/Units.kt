package com.mvvm.app.data.remote.model.unit


import com.google.gson.annotations.SerializedName

data class Units(
    @SerializedName("label")
    var label: String?,
    @SerializedName("value")
    var value: Int?
)