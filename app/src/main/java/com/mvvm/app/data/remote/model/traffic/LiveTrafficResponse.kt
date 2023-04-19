package com.mvvm.app.data.remote.model.traffic


import com.google.gson.annotations.SerializedName

data class LiveTrafficResponse(
    @SerializedName("data")
    var data: Map<String, Traffic>?,
    @SerializedName("refresh_time")
    var refreshTime: Int? = 120,
    @SerializedName("type")
    var type: Int?
)