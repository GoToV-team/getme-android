package com.gotov.getmeapp.main.plans.model.repository

import com.gotov.getmeapp.main.plan.model.data.Plan
import com.gotov.getmeapp.main.plans.model.api.PlansApi
import com.gotov.getmeapp.main.plans.model.data.Menti
import com.gotov.getmeapp.main.search.model.data.User
import retrofit2.Response

class PlansRepository(private val plansApi: PlansApi) {
    suspend fun getUser(): Response<User> {
        return plansApi.getUser()
    }

    suspend fun getMentorPlans(): Response<List<Plan>> {
        return plansApi.getMentorPlans()
    }

    suspend fun getMentiPlans(): Response<List<Plan>> {
        return plansApi.getMentiPlans()
    }

    suspend fun getMentis(): Response<List<Menti>> {
        return plansApi.getMentis()
    }
}
