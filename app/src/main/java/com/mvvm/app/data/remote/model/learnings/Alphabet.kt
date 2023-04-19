package com.mvvm.app.data.remote.model.learnings


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Alphabet(
    @SerializedName("Image")
    @Expose
    var image: String?,
    @SerializedName("Key")
    @Expose
    var key: String?,
    @SerializedName("Description")
    @Expose
    var description: String?,
    @SerializedName("Title")
    @Expose
    var title: String?
)