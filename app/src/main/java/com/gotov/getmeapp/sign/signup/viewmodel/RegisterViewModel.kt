package com.gotov.getmeapp.sign.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotov.getmeapp.sign.signup.model.data.Register
import com.gotov.getmeapp.sign.signup.model.repository.RegisterRepository
import com.gotov.getmeapp.utils.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

private const val SUCCESS_CODE = 201
private const val INCORRECT_FIELD_CODE = 403

class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel() {
    private val _status = MutableStateFlow<Resource<RegisterStatus>>(Resource.Null())

    val status = _status.asStateFlow()

    fun register(register: Register) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _status.emit(Resource.Loading())
                    val response = registerRepository.register(register)
                    when (response.code()) {
                        SUCCESS_CODE -> _status.emit(Resource.Success(RegisterStatus.SUCCESS))
                        INCORRECT_FIELD_CODE -> _status.emit(
                            Resource.Error(
                                "",
                                RegisterStatus.INCORRECT_FIELD
                            )
                        )
                        else -> {
                            val body: String?
                            body = response.errorBody()?.source().toString()

                            _status.emit(Resource.Error(body, RegisterStatus.SERVER_ERROR))
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
}
