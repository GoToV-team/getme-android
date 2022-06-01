package com.gotov.getmeapp.main.profile.model.api

import com.gotov.getmeapp.main.profile.model.data.RequestMentor
import com.gotov.getmeapp.main.profile.model.data.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProfileApi {
    @GET("user")
    suspend fun getCurrentUser(): Response<User>

    @POST("offers")
    suspend fun startMentor(@Body mentor: RequestMentor): Response<Void>

    @GET("user/{id}")
    suspend fun getUserById(@Path("id") id: Int): Response<User>
}
