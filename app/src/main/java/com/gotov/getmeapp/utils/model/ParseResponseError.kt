package com.gotov.getmeapp.utils.model

import com.beloo.widget.chipslayoutmanager.util.log.Log
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.ResponseBody

private const val UnknownErr = "Неизваестная ошибка"

data class ErrorResponse(
    @JsonProperty("message") val message: String?
)

fun getResponseError(body: ResponseBody?): String? {
    var response: ErrorResponse? = null
    body?.string()?.let {
        response = ObjectMapper().readValue(it, ErrorResponse::class.java)
    }

    if (response == null) {
        Log.e("Response error", body?.string())
        response = ErrorResponse(UnknownErr)
    }
    return response?.message
}