package com.gotov.getmeapp.sign.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotov.getmeapp.main.search.model.data.Skill
import com.gotov.getmeapp.sign.signup.model.data.Register
import com.gotov.getmeapp.sign.signup.model.repository.RegisterRepository
import com.gotov.getmeapp.utils.model.Resource
import com.gotov.getmeapp.utils.model.getResponseError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

private const val SUCCESS_REGISTER_CODE = 201
private const val SUCCESS_SKILL_CODE = 200
private const val INCORRECT_FIELD_CODE = 403

class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel() {
    private val _status = MutableStateFlow<Resource<RegisterStatus>>(Resource.Null())
    private val _skills = MutableStateFlow<Resource<List<String>>>(Resource.Null())

    val status = _status.asStateFlow()
    val skills = _skills.asStateFlow()

    fun register(register: Register) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _status.emit(Resource.Loading())
                    val response = registerRepository.register(register)
                    when (response.code()) {
                        SUCCESS_REGISTER_CODE -> _status.emit(Resource.Success(RegisterStatus.SUCCESS))
                        INCORRECT_FIELD_CODE -> _status.emit(
                            Resource.Error(
                                "",
                                RegisterStatus.INCORRECT_FIELD
                            )
                        )
                        else -> {
                            _status.emit(Resource.Error(getResponseError(response.errorBody()),
                                RegisterStatus.SERVER_ERROR))
                        }
                    }
                } catch (e: IOException) {
                    _status.emit(
                        Resource.Error(
                            "Err when try register: " + e.message,
                            null
                        )
                    )
                } catch (e: HttpException) {
                    _status.emit(
                        Resource.Error(
                            "Err when try register: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }

    fun getSkills() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _skills.emit(Resource.Loading())
                    val response = registerRepository.getSkills()
                    when (response.code()) {
                        SUCCESS_SKILL_CODE -> {
                            val res: MutableList<String> = ArrayList()
                            response.body()?.let {
                                for (skill in it.skills) {
                                    res.add(skill.name)
                                }
                            }
                            _skills.emit(Resource.Success(res))
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
}
