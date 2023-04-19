package com.mvvm.app.data.remote.model.vehicle


import com.google.gson.annotations.SerializedName

data class RegisterVehicle(
    @SerializedName("license_plate_number")
    var licensePlateNumber: String?,
    @SerializedName("mobile_no")
    var mobileNo: String?,
    @SerializedName("unit_id")
    var unitId: Int?,
    @SerializedName("unit_name")
    var unitName: String?,
    @SerializedName("vehicle_log_id")
    var vehicleLogId: Int?
)