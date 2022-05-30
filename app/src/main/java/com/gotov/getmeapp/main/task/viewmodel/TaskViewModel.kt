package com.gotov.getmeapp.main.task.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotov.getmeapp.main.task.model.data.Task
import com.gotov.getmeapp.main.task.model.repository.TaskRepository
import com.gotov.getmeapp.utils.model.Resource
import com.gotov.getmeapp.utils.model.getResponseError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

private const val SUCCESS_CODE = 200

class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    private val _task = MutableStateFlow<Resource<Task>>(Resource.Null())

    val task = _task.asStateFlow()

    fun markTask(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _task.emit(Resource.Loading())
                    val response = taskRepository.markTask(id)
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            val tsk = task.value
                            tsk.data?.run {
                                tsk.data.isDone = !tsk.data.isDone
                                _task.emit(Resource.Success(tsk.data))
                            }
                        }
                        else -> {
                            _task.emit(Resource.Error(getResponseError(response.errorBody())))
                        }
                    }
                } catch (e: IOException) {
                    _task.emit(
                        Resource.Error(
                            "Err when try get task: " + e.message,
                            null
                        )
                    )
                } catch (e: HttpException) {
                    _task.emit(
                        Resource.Error(
                            "Err when try get task: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }

    fun getTask(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _task.emit(Resource.Loading())
                    val response = taskRepository.getTask(id)
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            response.body()?.let {
                                _task.emit(Resource.Success(it))
                            }
                        }
                        else -> {
                            _task.emit(Resource.Error(getResponseError(response.errorBody())))
                        }
                    }
                } catch (e: IOException) {
                    _task.emit(
                        Resource.Error(
                            "Err when try get task: " + e.message,
                            null
                        )
                    )
                } catch (e: HttpException) {
                    _task.emit(
                        Resource.Error(
                            "Err when try get task: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }

    suspend fun update(title: String, description: String) {
        taskRepository.updateTask(
            _task.value.data!!.id,
            Task(
                _task.value.data!!.id,
                title, description,
                _task.value.data!!.isDone,
                null
            )
        )
    }
}
