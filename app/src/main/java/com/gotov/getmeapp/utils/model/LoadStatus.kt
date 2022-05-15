package com.gotov.getmeapp.utils.model

import kotlin.Suppress

@Suppress("DataClassPrivateConstructor")
data class LoadingState(val status: Status, val msg: String? = null) {
    companion object {
        val loaded = LoadingState(Status.SUCCESS)
        val loading = LoadingState(Status.RUNNING)
        fun error(msg: String?) = LoadingState(Status.FAILED, msg)
    }
}

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}
