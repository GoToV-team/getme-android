package com.gotov.getmeapp.main.search.model.api

import com.gotov.getmeapp.main.search.model.data.SkillResponse
import com.gotov.getmeapp.main.search.model.data.SkillResponseItem
import com.gotov.getmeapp.main.search.model.data.User
import com.gotov.getmeapp.main.search.model.data.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("skills")
    suspend fun getSkills(): Response<SkillResponse>

    @GET("skills/users")
    suspend fun search(@Query("skills") skills: List<String>): Response<UserResponse>
}
