package com.gotov.getmeapp.main.editprofile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotov.getmeapp.app.preference.AppPreferences
import com.gotov.getmeapp.main.editprofile.model.data.UpdateUser
import com.gotov.getmeapp.main.editprofile.model.data.User
import com.gotov.getmeapp.main.editprofile.model.repository.EditProfileRepository
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

class EditProfileViewModel(
    private val editProfileRepository: EditProfileRepository
) : ViewModel(), KoinComponent {

    private val appPreferences by inject<AppPreferences>()

    private val _skills = MutableStateFlow<Resource<List<String>>>(Resource.Null())
    private val _user = MutableStateFlow<Resource<User>>(Resource.Null())
    private val _isUpdated = MutableStateFlow<Resource<Boolean>>(Resource.Null())
    private val _statusUser = MutableStateFlow<Resource<Boolean>>(Resource.Null())

    val user = _user.asStateFlow()
    val isUpdated = _isUpdated.asStateFlow()
    val statusUser = _statusUser.asStateFlow()
    val skills = _skills.asStateFlow()

    fun getCurrentUser() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _user.emit(Resource.Loading())
                    val response = editProfileRepository.getCurrentUser()
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            response.body()?.let {
                                _user.emit(Resource.Success(it))
                            }
                        }
                        else -> {
                            _user.emit(Resource.Error(getResponseError(response.errorBody())))
                        }
                    }
                } catch (e: IOException) {
                    _user.emit(
                        Resource.Error(
                            "Err when try get user: " + e.message,
                            null
                        )
                    )
                } catch (e: HttpException) {
                    _user.emit(
                        Resource.Error(
                            "Err when try get user: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }

    fun setNullUpdateStatus() {
        viewModelScope.launch {
            _isUpdated.emit(Resource.Null())
        }
    }

    fun updateUser(user: UpdateUser) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _isUpdated.emit(Resource.Loading())
                    val response = editProfileRepository.updateUser(user)
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            _isUpdated.emit(Resource.Success(true))
                        }
                        else -> {
                            _isUpdated.emit(Resource.Error(getResponseError(response.errorBody())))
                        }
                    }
                } catch (e: IOException) {
                    _isUpdated.emit(
                        Resource.Error(
                            "Err when try update user: " + e.message,
                            null
                        )
                    )
                } catch (e: HttpException) {
                    _isUpdated.emit(
                        Resource.Error(
                            "Err when try update user: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }

    fun setNullStatus() {
        viewModelScope.launch {
            _statusUser.emit(Resource.Null())
        }
    }

    fun changeStatus() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _statusUser.emit(Resource.Loading())
                    val response = editProfileRepository.changeStatus()
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            _statusUser.emit(Resource.Success(true))
                        }
                        else -> {
                            _statusUser.emit(Resource.Error(getResponseError(response.errorBody())))
                        }
                    }
                } catch (e: IOException) {
                    _statusUser.emit(
                        Resource.Error(
                            "Err when try update user status: " + e.message,
                            null
                        )
                    )
                } catch (e: HttpException) {
                    _statusUser.emit(
                        Resource.Error(
                            "Err when try update user status: " + e.message,
                            null
                        )
                    )
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
                    val response = editProfileRepository.getSkills()
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            response.body()?.let {
                                val tmp = it.skills.map { skill -> skill.name }
                                appPreferences.putHashSet(AppPreferences.Skills, HashSet(tmp))
                                _skills.emit(Resource.Success(tmp))
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
}
