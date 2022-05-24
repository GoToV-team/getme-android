package com.gotov.getmeapp.main.plan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotov.getmeapp.main.plan.model.data.Plan
import com.gotov.getmeapp.main.plan.model.repository.PlanRepository
import com.gotov.getmeapp.utils.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlanViewModel(private val planRepository: PlanRepository) : ViewModel() {
    private val _plan = MutableStateFlow<Resource<Plan>>(Resource.Null())

    val plan = _plan.asStateFlow()

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
                } catch (e: Exception) {
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

    companion object {
        private const val SUCCESS_CODE = 200
    }
}
