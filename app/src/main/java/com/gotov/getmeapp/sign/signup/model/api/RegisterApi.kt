package com.gotov.getmeapp.sign.signup.model.api

import com.gotov.getmeapp.sign.signup.model.data.Register
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {
    @POST("auth/simple/register")
    suspend fun register(@Body register: Register): Response<Void>
}
