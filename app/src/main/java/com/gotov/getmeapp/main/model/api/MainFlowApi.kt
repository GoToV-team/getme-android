package com.gotov.getmeapp.main.model.api

import retrofit2.Response
import retrofit2.http.POST

interface MainFlowApi {
    @POST("logout")
    suspend fun logout(): Response<Void>
}
