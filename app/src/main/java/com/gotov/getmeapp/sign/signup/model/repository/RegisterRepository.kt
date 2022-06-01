package com.gotov.getmeapp.sign.signup.model.repository

import com.gotov.getmeapp.sign.signup.model.api.RegisterApi
import com.gotov.getmeapp.sign.signup.model.data.Register
import com.gotov.getmeapp.sign.signup.model.data.SkillResponse
import com.gotov.getmeapp.sign.signup.model.data.UpdateUser
import retrofit2.Response

class RegisterRepository(private val registerApi: RegisterApi) {
    suspend fun register(register: Register): Response<Void> {
        return registerApi.register(register)
    }

    suspend fun getSkills(): Response<SkillResponse> {
        return registerApi.getSkills()
    }

    suspend fun updateUser(updateUser: UpdateUser): Response<Void> {
        return registerApi.updateUser(updateUser)
    }
}
