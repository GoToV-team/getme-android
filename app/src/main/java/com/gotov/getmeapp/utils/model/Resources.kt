package com.gotov.getmeapp.utils.model

import com.gotov.getmeapp.utils.model.LoadingState.Companion.error
import com.gotov.getmeapp.utils.model.LoadingState.Companion.loaded
import com.gotov.getmeapp.utils.model.LoadingState.Companion.loading

data class Resource<out T>(val state: LoadingState, val data: T?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(loaded, data)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(error(msg), data)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(loading, data)
        }

    }

}