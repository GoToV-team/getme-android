package com.gotov.getmeapp.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotov.getmeapp.app.preference.AppPreferences
import com.gotov.getmeapp.main.model.repository.MainFlowRepository
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
private const val INCORRECT_FIELD_CODE = 403

class MainFlowViewModel(
    private val mainFlowRepository: MainFlowRepository
) : ViewModel(), KoinComponent {
    private val appPreferences by inject<AppPreferences>()
    private val _status = MutableStateFlow<Resource<Boolean>>(Resource.Null())

    val status = _status.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _status.emit(Resource.Loading())
                    val response = mainFlowRepository.logout()
                    appPreferences.putHashSet(
                        AppPreferences.Cookies,
                        null
                    )
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            _status.emit(Resource.Success(true))
                        }
                        else -> {
                            _status.emit(
                                Resource.Error(
                                    getResponseError(response.errorBody())
                                )
                            )
                        }
                    }
                } catch (e: IOException) {
                    _status.emit(
                        Resource.Error(
                            "Err when try logout: " + e.message,
                            null
                        )
                    )
                } catch (e: HttpException) {
                    _status.emit(
                        Resource.Error(
                            "Err when try logout: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }
}
