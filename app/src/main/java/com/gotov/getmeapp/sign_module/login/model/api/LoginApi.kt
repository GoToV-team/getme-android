package com.gotov.getmeapp.sign_module.login.model.api

import com.gotov.getmeapp.sign_module.login.model.data.Login
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("login")
    fun login(@Body login: Login): Observable<Response<Void>>
}
