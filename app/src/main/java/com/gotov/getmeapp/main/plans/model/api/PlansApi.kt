package com.gotov.getmeapp.main.plans.model.api

import com.gotov.getmeapp.main.plans.model.data.IsMentorResponse
import com.gotov.getmeapp.main.plans.model.data.Mentis
import com.gotov.getmeapp.main.plans.model.data.OffersRequest
import com.gotov.getmeapp.main.plans.model.data.Plan
import com.gotov.getmeapp.main.plans.model.data.PlansResponse
import com.gotov.getmeapp.main.plans.model.data.SkillResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PlansApi {
    @GET("skills")
    suspend fun getSkills(): Response<SkillResponse>

    @GET("user/status")
    suspend fun getIsMentor(): Response<IsMentorResponse>

    @GET("plans")
    suspend fun getPlans(@Query("role") role: String): Response<PlansResponse>

    @POST("offer/{id}/accept")
    suspend fun applyMenti(@Path("id") id: Int, @Body body: OffersRequest): Response<Plan>

    @DELETE("offer/{id}/accept")
    suspend fun cancelMenti(@Path("id") id: Int): Response<Void>

    @GET("offers")
    suspend fun getMentis(): Response<Mentis>
}
