package com.gotov.getmeapp.main.profile.model.repository

import com.gotov.getmeapp.main.profile.model.api.ProfileApi
import com.gotov.getmeapp.main.search.model.data.User
import retrofit2.Response

class ProfileRepository(private val profileApi: ProfileApi) {
    suspend fun getCurrentUser(): Response<User> {
        return profileApi.getCurrentUser()
    }
    suspend fun getUserById(id: Int): Response<User> {
        return profileApi.getUserById(id)
    }
}
