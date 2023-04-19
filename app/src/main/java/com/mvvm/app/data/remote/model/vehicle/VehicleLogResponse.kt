package com.mvvm.app.data.remote.model.vehicle


import com.google.gson.annotations.SerializedName

data class VehicleLogResponse(
    @SerializedName("data")
    var vehicleData: List<VehicleData>?,
    @SerializedName("status")
    var status: Int?,
    @SerializedName("total_logs")
    var totalLogs: Int?
)