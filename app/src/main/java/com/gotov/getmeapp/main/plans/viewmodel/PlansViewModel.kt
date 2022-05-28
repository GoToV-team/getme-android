package com.gotov.getmeapp.main.plans.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotov.getmeapp.main.plan.model.data.Plan
import com.gotov.getmeapp.main.plans.model.data.Menti
import com.gotov.getmeapp.main.plans.model.repository.PlansRepository
import com.gotov.getmeapp.utils.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

private const val SUCCESS_CODE = 200

class PlansViewModel(private val plansRepository: PlansRepository) : ViewModel() {
    private val _mentorPlans = MutableStateFlow<Resource<List<Plan>>>(Resource.Null())
    private val _mentiPlans = MutableStateFlow<Resource<List<Plan>>>(Resource.Null())
    private val _mentis = MutableStateFlow<Resource<List<Menti>>>(Resource.Null())
    private val _isMentor = MutableStateFlow<Resource<Boolean>>(Resource.Null())

    val isMentor = _isMentor.asStateFlow()
    val mentis = _mentis.asStateFlow()
    val mentiPlans = _mentiPlans.asStateFlow()
    val mentorPlans = _mentorPlans.asStateFlow()

    fun getMentis() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _mentis.emit(Resource.Loading())
                    val response = plansRepository.getMentis()
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            response.body()?.let {
                                _mentis.emit(Resource.Success(it))
                            }
                        }
                        else -> {
                            val body: String?
                            body = response.body().toString()

                            _mentis.emit(Resource.Error(body))
                        }
                    }
                } catch (e: IOException) {
                    _mentis.emit(
                        Resource.Error(
                            "Err when try get mentis: " + e.message,
                            null
                        )
                    )
                } catch (e: HttpException) {
                    _mentis.emit(
                        Resource.Error(
                            "Err when try get mentis: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }

    fun getMentiPlans() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _mentiPlans.emit(Resource.Loading())
                    val response = plansRepository.getMentiPlans()
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            response.body()?.let {
                                _mentiPlans.emit(Resource.Success(it))
                            }
                        }
                        else -> {
                            val body: String?
                            body = response.body().toString()

                            _mentiPlans.emit(Resource.Error(body))
                        }
                    }
                } catch (e: IOException) {
                    _mentiPlans.emit(
                        Resource.Error(
                            "Err when try get mentis: " + e.message,
                            null
                        )
                    )
                } catch (e: HttpException) {
                    _mentiPlans.emit(
                        Resource.Error(
                            "Err when try get mentis: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }

    fun getMentorPlans() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _mentorPlans.emit(Resource.Loading())
                    val response = plansRepository.getMentorPlans()
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            response.body()?.let {
                                _mentorPlans.emit(Resource.Success(it))
                            }
                        }
                        else -> {
                            val body: String?
                            body = response.body().toString()

                            _mentorPlans.emit(Resource.Error(body))
                        }
                    }
                } catch (e: IOException) {
                    _mentiPlans.emit(
                        Resource.Error(
                            "Err when try get mentis: " + e.message,
                            null
                        )
                    )
                } catch (e: HttpException) {
                    _mentiPlans.emit(
                        Resource.Error(
                            "Err when try get mentis: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }

    fun getIsMentor() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _isMentor.emit(Resource.Loading())
                    val response = plansRepository.getUser()
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            response.body()?.let {
                                _isMentor.emit(Resource.Success(it.isMentor))
                            }
                        }
                        else -> {
                            val body: String?
                            body = response.body().toString()

                            _isMentor.emit(Resource.Error(body))
                        }
                    }
                }  catch (e: IOException) {
                    _isMentor.emit(
                        Resource.Error(
                            "Err when try get isMentor: " + e.message,
                            null
                        )
                    )
                } catch (e: HttpException) {
                    _isMentor.emit(
                        Resource.Error(
                            "Err when try get isMentor: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }
}
