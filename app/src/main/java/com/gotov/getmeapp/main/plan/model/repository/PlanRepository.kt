package com.gotov.getmeapp.main.plan.model.repository

import com.gotov.getmeapp.main.plan.model.api.PlanApi
import com.gotov.getmeapp.main.plan.model.data.Plan
import com.gotov.getmeapp.main.plan.model.data.TaskCreate
import com.gotov.getmeapp.main.plan.model.data.TaskResponse
import retrofit2.Response

class PlanRepository(private val planApi: PlanApi) {
    suspend fun getPlanById(id: Int): Response<Plan> {
        return planApi.getPlanById(id)
    }

    suspend fun applyTask(id: Int): Response<TaskResponse> {
        return planApi.applyTask(id)
    }

    suspend fun addTask(planId: Int, task: TaskCreate): Response<TaskResponse> {
        return planApi.addTask(planId, task)
    }
}
