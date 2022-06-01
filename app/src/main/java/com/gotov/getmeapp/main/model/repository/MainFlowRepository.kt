package com.gotov.getmeapp.main.model.repository

import com.gotov.getmeapp.main.model.api.MainFlowApi
import retrofit2.Response

class MainFlowRepository(private val mainFlowApi: MainFlowApi) {
    suspend fun logout(): Response<Void> {
        return mainFlowApi.logout()
    }
}
