package com.mvvm.app.data.remote.model.traffic


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("1")
    var traffic: Traffic?,
    @SerializedName("2")
    var x2: Traffic?,
    @SerializedName("3")
    var x3: Traffic?,
    @SerializedName("4")
    var x4: Traffic?,
    @SerializedName("5")
    var x5: Traffic?
)