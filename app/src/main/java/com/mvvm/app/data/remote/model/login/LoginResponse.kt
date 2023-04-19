package com.mvvm.app.data.remote.model.login


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token")
    var token: String?,
    @SerializedName("type")
    var type: Int?
)