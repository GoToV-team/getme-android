package com.gotov.getmeapp.sign.signup.model.repository

import com.gotov.getmeapp.sign.signup.model.api.RegisterApi
import com.gotov.getmeapp.sign.signup.model.data.Register
import retrofit2.Response

class RegisterRepository(private val registerApi: RegisterApi) {
    suspend fun register(register: Register): Response<Void> {
        return registerApi.register(register)
    }
}
