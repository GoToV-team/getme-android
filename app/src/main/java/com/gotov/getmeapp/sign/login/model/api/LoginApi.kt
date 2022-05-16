package com.gotov.getmeapp.sign.login.model.api

import com.gotov.getmeapp.sign.login.model.data.Login
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("login")
    suspend fun login(@Body login: Login): Response<Void>
}
