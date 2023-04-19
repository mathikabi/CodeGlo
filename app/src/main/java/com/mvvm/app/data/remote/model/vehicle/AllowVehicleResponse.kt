package com.mvvm.app.data.remote.model.vehicle


import com.google.gson.annotations.SerializedName

data class AllowVehicleResponse(
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Int?
)