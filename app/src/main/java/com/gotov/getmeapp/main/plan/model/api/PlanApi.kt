package com.gotov.getmeapp.main.plan.model.api

import com.gotov.getmeapp.main.plan.model.data.Plan
import com.gotov.getmeapp.main.plan.model.data.TaskCreate
import com.gotov.getmeapp.main.plan.model.data.TaskResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PlanApi {
    @GET("plans/{id}/tasks")
    suspend fun getPlanById(@Path("id") id: Int): Response<Plan>

    @PUT("tasks/{id}/apply")
    suspend fun applyTask(@Path("id") id: Int): Response<TaskResponse>

    @POST("plans/{id}/task")
    suspend fun addTask(@Path("id") planId: Int, @Body task: TaskCreate): Response<TaskResponse>
}
