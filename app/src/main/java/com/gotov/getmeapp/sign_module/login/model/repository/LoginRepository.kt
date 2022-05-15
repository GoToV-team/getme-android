package com.gotov.getmeapp.sign_module.login.model.repository

import com.gotov.getmeapp.sign_module.login.model.api.LoginApi
import com.gotov.getmeapp.sign_module.login.model.data.Login
import io.reactivex.Observable
import retrofit2.Response

class LoginRepository(private val loginApi: LoginApi) {
    fun login(login: Login): Observable<Response<Void>> {
        return loginApi.login(login)
    }
}