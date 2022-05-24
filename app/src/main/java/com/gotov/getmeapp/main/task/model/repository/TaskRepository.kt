package com.gotov.getmeapp.main.task.model.repository

import com.gotov.getmeapp.main.profile.model.api.ProfileApi
import com.gotov.getmeapp.main.search.model.data.User
import com.gotov.getmeapp.main.search.model.api.SearchApi
import com.gotov.getmeapp.main.search.model.data.Skill
import com.gotov.getmeapp.main.search.model.data.SkillResponse
import com.gotov.getmeapp.main.task.model.api.TaskApi
import com.gotov.getmeapp.main.task.model.data.Task
import retrofit2.Response

class TaskRepository(private val taskApi: TaskApi) {
    suspend fun markTask(id: Int): Response<Void> {
        return taskApi.markTask(id)
    }
    suspend fun getTask(id: Int): Response<Task> {
        return taskApi.getTask(id)
    }
}
