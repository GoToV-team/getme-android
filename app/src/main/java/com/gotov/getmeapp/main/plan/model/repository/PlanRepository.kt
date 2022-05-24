package com.gotov.getmeapp.main.plan.model.repository

import com.gotov.getmeapp.main.plan.model.api.PlanApi
import com.gotov.getmeapp.main.plan.model.data.Plan
import retrofit2.Response

class PlanRepository(private val planApi: PlanApi) {
    suspend fun getPlanById(id: Int): Response<Plan> {
        return planApi.getPlanById(id)
    }
}
