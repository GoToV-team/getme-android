package com.gotov.getmeapp.sign.login.model.repository

import com.gotov.getmeapp.sign.login.model.api.LoginApi
import com.gotov.getmeapp.sign.login.model.data.Login
import kotlinx.coroutines.Deferred
import retrofit2.Response

class LoginRepository(private val loginApi: LoginApi) {
    fun loginAsync(login: Login): Deferred<Response<Void>> {
        return loginApi.loginAsync(login)
    }
}
