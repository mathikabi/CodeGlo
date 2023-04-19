package com.mvvm.app.data.remote.model.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(@SerializedName("ResponseStatus")
                           var responseStatus: ResponseStatus,
                           @SerializedName("ResponseData")
                           var responseData: T?
)