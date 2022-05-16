package com.gotov.getmeapp.sign.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
import kotlinx.coroutines.withContext

private const val SUCCESS_CODE = 200
private const val INCORRECT_FIELD_CODE = 403

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    private val _status = MutableStateFlow<Resource<LoginStatus>>(Resource.loading(null))

    private val status = _status.asStateFlow()

    override fun onCleared() {
        super.onCleared()
        _status.value = Resource.loading(null)
    }

    suspend fun login(login: Login): StateFlow<Resource<LoginStatus>> {
        withContext(Dispatchers.IO) {
            try {
                val response = loginRepository.loginAsync(login).await()
                when (response.code()) {
                    SUCCESS_CODE -> _status.value = Resource.success(LoginStatus.SUCCESS)
                    INCORRECT_FIELD_CODE -> _status.value = Resource.error(
                        "",
                        LoginStatus.INCORRECT_FIELD
                    )
                    else -> {
                        val body: String?
                        body = response.body().toString()

                        _status.value = Resource.error(body, LoginStatus.SERVER_ERROR)
                    }
                }
            } catch (e: Exception) {
                _status.value = Resource.error(
                    "Err when try login: " + e.message,
                    null
                )
            }
        }

        return status
    }
}
