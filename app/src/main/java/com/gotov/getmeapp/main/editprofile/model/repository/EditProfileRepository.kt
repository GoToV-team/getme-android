package com.gotov.getmeapp.main.editprofile.model.repository

import com.gotov.getmeapp.main.editprofile.model.api.EditProfileApi
import com.gotov.getmeapp.main.editprofile.model.data.SkillResponse
import com.gotov.getmeapp.main.editprofile.model.data.UpdateUser
import com.gotov.getmeapp.main.editprofile.model.data.User
import retrofit2.Response

class EditProfileRepository(private val editProfileApi: EditProfileApi) {
    suspend fun getSkills(): Response<SkillResponse> {
        return editProfileApi.getSkills()
    }

    suspend fun getCurrentUser(): Response<User> {
        return editProfileApi.getCurrentUser()
    }

    suspend fun changeStatus(): Response<Void> {
        return editProfileApi.changeStatus()
    }

    suspend fun updateUser(updateUser: UpdateUser): Response<Void> {
        return editProfileApi.updateUser(updateUser)
    }
}
