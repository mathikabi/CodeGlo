package com.mvvm.app.data.remote.model.login


import com.google.gson.annotations.SerializedName

data class LoginReq(
    @SerializedName("password")
    var password: String?,
    @SerializedName("username")
    var username: String?
)