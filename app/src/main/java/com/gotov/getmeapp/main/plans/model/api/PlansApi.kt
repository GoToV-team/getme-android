package com.gotov.getmeapp.main.plans.model.api

import com.gotov.getmeapp.main.plan.model.data.Plan
import com.gotov.getmeapp.main.plans.model.data.Menti
import com.gotov.getmeapp.main.search.model.data.User
import retrofit2.Response
import retrofit2.http.GET

interface PlansApi {
    @GET("user")
    suspend fun getUser(): Response<User>

    @GET("mentor/plans")
    suspend fun getMentorPlans(): Response<List<Plan>>

    @GET("menti/plans")
    suspend fun getMentiPlans(): Response<List<Plan>>

    @GET("mentis")
    suspend fun getMentis(): Response<List<Menti>>
}
