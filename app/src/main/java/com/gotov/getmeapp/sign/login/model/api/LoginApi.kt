package com.gotov.getmeapp.sign.login.model.api

import com.gotov.getmeapp.sign.login.model.data.Login
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("login")
    fun loginAsync(@Body login: Login): Deferred<Response<Void>>
}
