package com.gotov.getmeapp.main.search.model.repository

import com.gotov.getmeapp.main.search.model.api.SearchApi
import com.gotov.getmeapp.main.search.model.data.Skill
import com.gotov.getmeapp.main.search.model.data.SkillResponse
import com.gotov.getmeapp.main.search.model.data.UserResponse
import retrofit2.Response

class SearchRepository(private val searchApi: SearchApi) {
    suspend fun getSkills(): Response<SkillResponse> {
        return searchApi.getSkills()
    }
    suspend fun search(skills: List<Skill>): Response<UserResponse> {
        return searchApi.search(skills.map { it.name })
    }
}
