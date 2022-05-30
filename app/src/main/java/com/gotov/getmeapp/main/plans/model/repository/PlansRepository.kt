package com.gotov.getmeapp.main.plans.model.repository

import com.gotov.getmeapp.main.plans.model.api.PlansApi
import com.gotov.getmeapp.main.plans.model.data.*
import retrofit2.Response

class PlansRepository(private val plansApi: PlansApi) {
    companion object {
        private const val mentor = "mentor"
        private const val menti = "mentee"
    }

    suspend fun getIsMentor(): Response<IsMentorResponse> {
        return plansApi.getIsMentor()
    }

    suspend fun getPlansAsMentor(): Response<PlansResponse> {
        return plansApi.getPlans(mentor)
    }

    suspend fun getPlansAsMenti(): Response<PlansResponse> {
        return plansApi.getPlans(menti)
    }

    suspend fun getSkills(): Response<SkillResponse> {
        return plansApi.getSkills()
    }

    suspend fun applyMenti(id: Int, body: OffersRequest): Response<Plan> {
        return plansApi.applyMenti(id, body)
    }

    suspend fun cancelMenti(id: Int): Response<Void> {
        return plansApi.cancelMenti(id)
    }

    suspend fun getMentis(): Response<Mentis> {
        return plansApi.getMentis()
    }
}
