package com.mvvm.app.data.remote.model.base

import androidx.annotation.Nullable

class Resource<T> private constructor(val status: Status, @param:Nullable @field:Nullable val data: T?,
                                      @param:Nullable @field:Nullable val message: String?) {

    enum class Status {
        SUCCESS, ERROR, FAILURE
    }

    companion object {

        fun <T> success(msg: String, @Nullable data: T?): Resource<T> {
            return Resource<T>(
                Status.SUCCESS,
                data,
                msg
            )
        }

        fun <T> error(msg: String, @Nullable data: T?): Resource<T> {
            return Resource<T>(
                Status.ERROR,
                data,
                msg
            )
        }

        /*fun <T> loading(@Nullable data: T): Resource<T> {
            return Resource<T>(
                Status.LOADING,
                data,
                null
            )
        }*/

        fun <T> failure(msg: String, @Nullable data: T?): Resource<T> {
            return Resource<T>(
                Status.FAILURE,
                data,
                msg
            )
        }
    }
}