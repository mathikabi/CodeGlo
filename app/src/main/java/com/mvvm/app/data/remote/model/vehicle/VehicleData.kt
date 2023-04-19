package com.mvvm.app.data.remote.model.vehicle


import com.google.gson.annotations.SerializedName

data class VehicleData(
    @SerializedName("authorised")
    var authorised: Boolean?,
    @SerializedName("blacklisted")
    var blacklisted: Boolean?,
    @SerializedName("img_path")
    var imgPath: ArrayList<String>?,
    @SerializedName("lane_id")
    var laneId: Int?,
    @SerializedName("lane_name")
    var laneName: String?,
    @SerializedName("license_plate_number")
    var licensePlateNumber: String?,
    @SerializedName("log_id")
    var logId: Int?,
    @SerializedName("timestamp")
    var timestamp: String?,
    @SerializedName("unit_name")
    var unitName: String?,
    var dateStr: String?,
    var timeStr: String?,
    var background: Int? = 0,
    var mLive: Boolean?
)