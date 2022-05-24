package com.gotov.getmeapp.main.plan.model.api

import com.gotov.getmeapp.main.plan.model.data.Plan
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PlanApi {
    @GET("plan/{id}")
    suspend fun getPlanById(@Path("id") id: Int): Response<Plan>
}
