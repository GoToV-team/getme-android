package com.gotov.getmeapp.main.search.model.repository

import com.gotov.getmeapp.main.search.model.data.User
import com.gotov.getmeapp.main.search.model.api.SearchApi
import com.gotov.getmeapp.main.search.model.data.Skill
import com.gotov.getmeapp.main.search.model.data.SkillResponse
import retrofit2.Response

class SearchRepository(private val searchApi: SearchApi) {
    suspend fun getSkills(): Response<Array<SkillResponse>> {
        return searchApi.getSkills()
    }
    suspend fun search(skills: List<Skill>): Response<Array<User>> {
        return searchApi.search(skills.map { it.name })
    }
}
