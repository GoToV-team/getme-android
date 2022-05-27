package com.gotov.getmeapp.sign.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotov.getmeapp.sign.login.model.data.Login
import com.gotov.getmeapp.sign.login.model.repository.LoginRepository
import com.gotov.getmeapp.utils.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val SUCCESS_CODE = 200
private const val INCORRECT_FIELD_CODE = 403

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    private val _status = MutableStateFlow<Resource<LoginStatus>>(Resource.Null())

    val status = _status.asStateFlow()

    fun login(login: Login) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _status.emit(Resource.Loading())
                    val response = loginRepository.login(login)
                    when (response.code()) {
                        SUCCESS_CODE -> _status.emit(Resource.Success(LoginStatus.SUCCESS))
                        INCORRECT_FIELD_CODE -> _status.emit(
                            Resource.Error(
                                "",
                                LoginStatus.INCORRECT_FIELD
                            )
                        )
                        else -> {
                            val body: String?
                            body = response.errorBody().toString()

                            _status.emit(Resource.Error(body, LoginStatus.SERVER_ERROR))
                        }
                    }
                } catch (e: Exception) {
                    _status.emit(
                        Resource.Error(
                            "Err when try login: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }
}
