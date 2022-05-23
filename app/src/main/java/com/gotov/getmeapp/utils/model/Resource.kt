package com.gotov.getmeapp.utils.model

sealed class Resource<out T>(val data: T?, val msg: String?) {
    class Success<T>(data: T) : Resource<T>(data, null)

    class Error<T>(message: String?, data: T? = null) : Resource<T>(data, message)

    class Loading<T> : Resource<T>(null, null)

    class Null<T> : Resource<T>(null, null)
}
