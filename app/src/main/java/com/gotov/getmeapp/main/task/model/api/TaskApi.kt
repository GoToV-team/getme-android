package com.gotov.getmeapp.main.task.model.api

import com.gotov.getmeapp.main.task.model.data.Task
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskApi {
    @GET("task/{id}")
    suspend fun getTask(@Path("id") id: Int): Response<Task>

    @PUT("task/{id}/mark")
    suspend fun markTask(@Path("id") id: Int): Response<Void>
}
