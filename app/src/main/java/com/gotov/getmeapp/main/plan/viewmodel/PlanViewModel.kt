package com.gotov.getmeapp.main.plan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotov.getmeapp.main.plan.model.data.Plan
import com.gotov.getmeapp.main.plan.model.data.TaskCreate
import com.gotov.getmeapp.main.plan.model.repository.PlanRepository
import com.gotov.getmeapp.utils.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class PlanViewModel(private val planRepository: PlanRepository) : ViewModel() {
    private val _plan = MutableStateFlow<Resource<Plan>>(Resource.Null())
    private val _statusCreating = MutableStateFlow<Resource<Boolean>>(Resource.Null())
    private val _statusApplying = MutableStateFlow<Resource<Boolean>>(Resource.Null())

    val plan = _plan.asStateFlow()
    val statusCreating = _statusCreating.asStateFlow()
    val statusApplying = _statusApplying.asStateFlow()

    fun getPlanById(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _plan.emit(Resource.Loading())
                    val response = planRepository.getPlanById(id)
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            response.body()?.let {
                                _plan.emit(Resource.Success(it))
                            }
                        }
                        else -> {
                            val body: String?
                            body = response.body().toString()

                            _plan.emit(Resource.Error(body))
                        }
                    }
                } catch (e: IOException) {
                    _plan.emit(
                        Resource.Error(
                            "Err when try get plan: " + e.message,
                            null
                        )
                    )
                } catch (e: HttpException) {
                    _plan.emit(
                        Resource.Error(
                            "Err when try get plan: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }

    fun setNullStatusApplying() {
        viewModelScope.launch {
            _statusApplying.emit(Resource.Null())
        }
    }

    fun applyTask(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _statusApplying.emit(Resource.Loading())
                    val response = planRepository.applyTask(id)
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            _statusApplying.emit(Resource.Success(true))
                        }
                        else -> {
                            val body: String?
                            body = response.body().toString()

                            _statusApplying.emit(Resource.Error(body))
                        }
                    }
                } catch (e: IOException) {
                    _statusApplying.emit(
                        Resource.Error(
                            "Err when try apply task: " + e.message,
                            null
                        )
                    )
                } catch (e: HttpException) {
                    _statusApplying.emit(
                        Resource.Error(
                            "Err when try apply task: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }

    fun setNullStatusCreating() {
        viewModelScope.launch {
            _statusCreating.emit(Resource.Null())
        }
    }

    fun addTask(planId: Int, task: TaskCreate) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _statusCreating.emit(Resource.Loading())
                    val response = planRepository.addTask(planId, task)
                    when (response.code()) {
                        SUCCESS_CODE_CREATE -> {
                            _statusCreating.emit(Resource.Success(true))
                        }
                        else -> {
                            val body: String?
                            body = response.body().toString()

                            _statusCreating.emit(Resource.Error(body))
                        }
                    }
                } catch (e: IOException) {
                    _statusCreating.emit(
                        Resource.Error(
                            "Err when try add task: " + e.message,
                            null
                        )
                    )
                } catch (e: HttpException) {
                    _statusCreating.emit(
                        Resource.Error(
                            "Err when try add task: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }

    companion object {
        private const val SUCCESS_CODE = 200
        private const val SUCCESS_CODE_CREATE = 201
    }
}
