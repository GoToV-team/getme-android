package com.gotov.getmeapp.sign.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotov.getmeapp.sign.login.model.data.Login
import com.gotov.getmeapp.sign.login.model.repository.LoginRepository
import com.gotov.getmeapp.utils.model.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val SUCCESS_CODE = 200
private const val INCORRECT_FIELD_CODE = 403

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    private val _status = MutableStateFlow<Resource<LoginStatus>>(Resource.loading(null))

    val status = _status.asStateFlow()

    fun login(login: Login){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = loginRepository.login(login)
                    when (response.code()) {
                        SUCCESS_CODE -> _status.emit(Resource.success(LoginStatus.SUCCESS))
                        INCORRECT_FIELD_CODE -> _status.emit(
                            Resource.error(
                                "",
                                LoginStatus.INCORRECT_FIELD
                            )
                        )
                        else -> {
                            val body: String?
                            body = response.body().toString()

                            _status.emit(Resource.error(body, LoginStatus.SERVER_ERROR))
                        }
                    }
                } catch (e: Exception) {
                    _status.emit(
                        Resource.error(
                            "Err when try login: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }
}
