package com.gotov.getmeapp.main.editprofile.model.api

import com.gotov.getmeapp.main.editprofile.model.data.SkillResponse
import com.gotov.getmeapp.main.editprofile.model.data.UpdateUser
import com.gotov.getmeapp.main.editprofile.model.data.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface EditProfileApi {
    @GET("skills")
    suspend fun getSkills(): Response<SkillResponse>

    @GET("user")
    suspend fun getCurrentUser(): Response<User>

    @PUT("user/status")
    suspend fun changeStatus(): Response<Void>

    @PUT("user")
    suspend fun updateUser(@Body updateUser: UpdateUser): Response<Void>
}
