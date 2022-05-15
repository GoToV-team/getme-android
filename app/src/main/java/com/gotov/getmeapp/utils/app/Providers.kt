package com.gotov.getmeapp.utils.app

import com.gotov.getmeapp.sign_module.login.model.api.LoginApi
import retrofit2.Retrofit

inline fun <reified T> provideApi(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}

inline fun <reified Repository, reified Api> provideRepository(api: Api): Repository {
    return Repository::class.java.getConstructor().newInstance(api)
}