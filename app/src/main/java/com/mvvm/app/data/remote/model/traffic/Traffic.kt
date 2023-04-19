package com.mvvm.app.data.remote.model.traffic


import com.google.gson.annotations.SerializedName

data class Traffic(
    @SerializedName("camera_name")
    var cameraName: String? = "",
    @SerializedName("img_path")
    var imgPath: String?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("vehicles_per_minute")
    var vehiclesPerMinute: Int?,
    var isLast: Boolean = false,
    var title: String = "",
    var img: Int?
)