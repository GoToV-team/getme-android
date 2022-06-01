package com.gotov.getmeapp.main.plans.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotov.getmeapp.app.preference.AppPreferences
import com.gotov.getmeapp.main.plans.model.data.Menti
import com.gotov.getmeapp.main.plans.model.data.OffersRequest
import com.gotov.getmeapp.main.plans.model.data.Plan
import com.gotov.getmeapp.main.plans.model.repository.PlansRepository
import com.gotov.getmeapp.utils.model.Resource
import com.gotov.getmeapp.utils.model.getResponseError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException
import java.io.IOException

private const val SUCCESS_CODE = 200
private const val SUCCESS_CODE_ACCEPT_MENTI = 201

class PlansViewModel(private val plansRepository: PlansRepository) : ViewModel(), KoinComponent {
    private val appPreferences by inject<AppPreferences>()

    private val _plans = MutableStateFlow<Resource<List<Plan>>>(Resource.Null())
    private val _mentis = MutableStateFlow<Resource<List<Menti>>>(Resource.Null())
    private val _isMentor = MutableStateFlow<Resource<Boolean>>(Resource.Null())
    private val _skills = MutableStateFlow<Resource<List<String>>>(Resource.Null())
    private val _newPlan = MutableStateFlow<Resource<Plan>>(Resource.Null())

    val isMentor = _isMentor.asStateFlow()
    val mentis = _mentis.asStateFlow()
    val plans = _plans.asStateFlow()
    val skills = _skills.asStateFlow()
    val newPlan = _newPlan.asStateFlow()

    fun getMentis() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _mentis.emit(Resource.Loading())
                    val response = plansRepository.getMentis()
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            response.body()?.let {
                                _mentis.emit(Resource.Success(it.mentis))
                            }
                        }
                        else -> {
                            _mentis.emit(Resource.Error(getResponseError(response.errorBody())))
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

    fun getPlansAsMenti() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _plans.emit(Resource.Loading())
                    val response = plansRepository.getPlansAsMenti()
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            response.body()?.let {
                                _plans.emit(Resource.Success(it.plans))
                            }
                        }
                        else -> {
                            _plans.emit(Resource.Error(getResponseError(response.errorBody())))
                        }
                    }
                } catch (e: IOException) {
                    _plans.emit(
                        Resource.Error(
                            "Err when try get plans: " + e.message,
                            null
                        )
                    )
                } catch (e: HttpException) {
                    _plans.emit(
                        Resource.Error(
                            "Err when try get plans: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }

    fun getPlansAsMentor() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _plans.emit(Resource.Loading())
                    val response = plansRepository.getPlansAsMentor()
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            response.body()?.let {
                                _plans.emit(Resource.Success(it.plans))
                            }
                        }
                        else -> {
                            _plans.emit(Resource.Error(getResponseError(response.errorBody())))
                        }
                    }
                } catch (e: IOException) {
                    _plans.emit(
                        Resource.Error(
                            "Err when try get plans: " + e.message,
                            null
                        )
                    )
                } catch (e: HttpException) {
                    _plans.emit(
                        Resource.Error(
                            "Err when try get plans: " + e.message,
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
                    _plans.emit(Resource.Loading())
                    val response = plansRepository.getIsMentor()
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            response.body()?.let {
                                _isMentor.emit(Resource.Success(it.isMentor))
                            }
                        }
                        else -> {
                            _plans.emit(Resource.Error(getResponseError(response.errorBody())))
                        }
                    }
                } catch (e: IOException) {
                    _plans.emit(
                        Resource.Error(
                            "Err when try get isMentor: " + e.message,
                            null
                        )
                    )
                } catch (e: HttpException) {
                    _plans.emit(
                        Resource.Error(
                            "Err when try get isMentor: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }

    fun applyMenti(id: Int, request: OffersRequest) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _newPlan.emit(Resource.Loading())
                    val response = plansRepository.applyMenti(id, request)
                    when (response.code()) {
                        SUCCESS_CODE_ACCEPT_MENTI -> {
                            response.body()?.let {
                                _newPlan.emit(Resource.Success(it))
                            }
                        }
                        else -> {
                            _newPlan.emit(Resource.Error(getResponseError(response.errorBody())))
                        }
                    }
                } catch (e: IOException) {
                    _newPlan.emit(
                        Resource.Error(
                            "Err when try apply menti: " + e.message,
                            null
                        )
                    )
                } catch (e: HttpException) {
                    _newPlan.emit(
                        Resource.Error(
                            "Err when try apply menti: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }

    fun cancelMenti(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val tmp = mentis.value
                try {
                    _mentis.emit(Resource.Loading())
                    val response = plansRepository.cancelMenti(id)
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            tmp.data?.run {
                                _mentis.emit(
                                    Resource.Success(
                                        this.filter { menti -> menti.offerId != id }
                                    )
                                )
                            }
                        }
                        else -> {
                            _mentis.emit(Resource.Error(getResponseError(response.errorBody())))
                            _mentis.emit(tmp)
                        }
                    }
                } catch (e: IOException) {
                    _mentis.emit(
                        Resource.Error(
                            "Err when try cancel menti: " + e.message,
                            null
                        )
                    )
                    _mentis.emit(tmp)
                } catch (e: HttpException) {
                    _mentis.emit(
                        Resource.Error(
                            "Err when try cancel menti: " + e.message,
                            null
                        )
                    )
                    _mentis.emit(tmp)
                }
            }
        }
    }

    fun getSkills() {
        viewModelScope.launch {
            val tmpSkills = appPreferences.getHashSet(AppPreferences.Skills)
            if (tmpSkills != null && tmpSkills.isNotEmpty()) {
                _skills.emit(Resource.Success(tmpSkills.toList()))
                return@launch
            }
            withContext(Dispatchers.IO) {
                try {
                    _skills.emit(Resource.Loading())
                    val response = plansRepository.getSkills()
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            response.body()?.let {
                                appPreferences.putHashSet(
                                    AppPreferences.Skills,
                                    HashSet(it.skills.map { skill -> skill.name })
                                )
                                _skills.emit(
                                    Resource.Success(
                                        it.skills.map { skill -> skill.name }
                                    )
                                )
                            }
                        }
                        else -> {
                            _skills.emit(Resource.Error(getResponseError(response.errorBody())))
                        }
                    }
                } catch (e: IOException) {
                    _skills.emit(
                        Resource.Error(
                            "Err when try get skills: " + e.message,
                            null
                        )
                    )
                } catch (e: HttpException) {
                    _skills.emit(
                        Resource.Error(
                            "Err when try get skills: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }

    fun clearNewPlan() {
        viewModelScope.launch {
            _newPlan.emit(Resource.Null())
        }
    }
}
