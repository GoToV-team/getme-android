package com.gotov.getmeapp.main.profile.model.api

import com.gotov.getmeapp.main.search.model.data.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApi {
    @GET("user")
    suspend fun getCurrentUser(): Response<User>

    @GET("user/{id}")
    suspend fun getUserById(@Path("id") id: Int): Response<User>
}
