package com.mvvm.app.data.remote.model.unit

import com.google.gson.annotations.SerializedName

class UnitResponse(
    @SerializedName("unit_data")
    val unitData: ArrayList<Units>
)