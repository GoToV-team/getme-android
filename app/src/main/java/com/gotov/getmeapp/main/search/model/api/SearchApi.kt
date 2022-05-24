package com.gotov.getmeapp.main.search.model.api

import com.gotov.getmeapp.main.search.model.data.User
import com.gotov.getmeapp.main.search.model.data.SkillResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("skills")
    suspend fun getSkills(): Response<Array<SkillResponse>>

    @GET("search")
    suspend fun search(@Query("skills") skills: List<String>): Response<Array<User>>
}
