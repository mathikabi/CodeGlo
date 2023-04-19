package com.mvvm.app.data.remote.model.learnings


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class LearningsResponse(
    @SerializedName("Alphabets")
    @Expose
    var alphabets: List<Alphabet>?
)