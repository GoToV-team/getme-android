package com.gotov.getmeapp.main.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotov.getmeapp.main.profile.model.data.RequestMentor
import com.gotov.getmeapp.main.profile.model.repository.ProfileRepository
import com.gotov.getmeapp.main.search.model.data.User
import com.gotov.getmeapp.utils.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val SUCCESS_CODE = 200

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {
    private val _user = MutableStateFlow<Resource<User>>(Resource.Null())
    private val _isAdded = MutableStateFlow<Resource<Boolean>>(Resource.Null())

    val user = _user.asStateFlow()
    val isAdded = _isAdded.asStateFlow()

    fun getCurrentUser() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _user.emit(Resource.Loading())
                    val response = profileRepository.getCurrentUser()
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            response.body()?.let {
                                _user.emit(Resource.Success(it))
                            }
                        }
                        else -> {
                            val body: String?
                            body = response.body().toString()
                            _user.emit(Resource.Error(body))
                        }
                    }
                } catch (e: Exception) {
                    _user.emit(
                        Resource.Error(
                            "Err when try get skills: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }

    fun startMentor(mentorId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _isAdded.emit(Resource.Loading())
                    val response = profileRepository.startMentor(RequestMentor(mentorId, "C++"))
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            _isAdded.emit(Resource.Success(true))
                        }
                        else -> {
                            val body: String?
                            body = response.body().toString()
                            _isAdded.emit(Resource.Error(body))
                        }
                    }
                } catch (e: Exception) {
                    _isAdded.emit(
                        Resource.Error(
                            "Err when try get skills: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }

    fun getUserById(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _user.emit(Resource.Loading())
                    val response = profileRepository.getUserById(id)
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            response.body()?.let {
                                _user.emit(Resource.Success(it))
                            }
                        }
                        else -> {
                            val body: String?
                            body = response.body().toString()

                            _user.emit(Resource.Error(body))
                        }
                    }
                } catch (e: Exception) {
                    _user.emit(
                        Resource.Error(
                            "Err when try get profile: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }
}
