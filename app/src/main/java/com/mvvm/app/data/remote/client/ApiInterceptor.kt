package com.mvvm.app.data.remote.client

import com.mvvm.app.utilities.Constants
import okhttp3.Interceptor
import okhttp3.Response


class ApiInterceptor : Interceptor {

    private var authToken = ""

    override fun intercept(chain: Interceptor.Chain): Response {
        authToken = ""
        val requestBuilder = chain.request().newBuilder()
            .build()
        return chain.proceed(requestBuilder)
    }
}
