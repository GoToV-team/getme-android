package com.gotov.getmeapp.sign.signup.model.api

import com.gotov.getmeapp.sign.signup.model.data.SkillResponse
import com.gotov.getmeapp.sign.signup.model.data.Register
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RegisterApi {
    @GET("skills")
    suspend fun getSkills(): Response<SkillResponse>

    @POST("auth/simple/register")
    suspend fun register(@Body register: Register): Response<Void>
}
