package com.gotov.getmeapp.main.profile.model.repository

import com.gotov.getmeapp.main.profile.model.api.ProfileApi
import com.gotov.getmeapp.main.profile.model.data.RequestMentor
import com.gotov.getmeapp.main.profile.model.data.User
import retrofit2.Response
import retrofit2.http.Body

class ProfileRepository(private val profileApi: ProfileApi) {
    suspend fun getCurrentUser(): Response<User> {
        return profileApi.getCurrentUser()
    }

    suspend fun startMentor(@Body mentor: RequestMentor): Response<Void> {
        return profileApi.startMentor(mentor)
    }

    suspend fun getUserById(id: Int): Response<User> {
        return profileApi.getUserById(id)
    }
}
