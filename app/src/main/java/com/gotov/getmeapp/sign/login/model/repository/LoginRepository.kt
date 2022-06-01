package com.gotov.getmeapp.sign.login.model.repository

import com.gotov.getmeapp.sign.login.model.api.LoginApi
import com.gotov.getmeapp.sign.login.model.data.Login
import retrofit2.Response

class LoginRepository(private val loginApi: LoginApi) {
    suspend fun login(login: Login): Response<Void> {
        return loginApi.login(login)
    }
}
