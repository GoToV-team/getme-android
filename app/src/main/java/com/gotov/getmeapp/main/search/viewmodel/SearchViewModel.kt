package com.gotov.getmeapp.main.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotov.getmeapp.main.search.model.data.Skill
import com.gotov.getmeapp.main.search.model.data.User
import com.gotov.getmeapp.main.search.model.repository.SearchRepository
import com.gotov.getmeapp.utils.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val SUCCESS_CODE = 200

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {
    private val _mentors = MutableStateFlow<Resource<List<User>>>(Resource.Null())

    private val _skills = MutableStateFlow<Resource<List<Skill>>>(Resource.Null())

    val skills = _skills.asStateFlow()
    val mentors = _mentors.asStateFlow()

    fun changeStateOfSkill(name: String, state: Boolean) {
        viewModelScope.launch {
            val skillsTmp = skills.value
            if (skillsTmp is Resource.Success && skillsTmp.data != null) {
                for (i in skillsTmp.data.indices) {
                    if (skillsTmp.data[i].name == name) {
                        skillsTmp.data[i].active = state
                    }
                }
                _skills.emit(skillsTmp)
            }
        }
    }

    fun getSkills() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _skills.emit(Resource.Loading())
                    val response = searchRepository.getSkills()
                    when (response.code()) {
                        SUCCESS_CODE -> {
                            val res: MutableList<Skill> = ArrayList()
                            response.body()?.let {
                                for (skill in it.skills) {
                                    res.add(Skill(skill.name, false))
                                }
                            }
                            _skills.emit(Resource.Success(res))
                        }
                        else -> {
                            val body: String?
                            body = response.body().toString()

                            _skills.emit(Resource.Error(body))
                        }
                    }
                } catch (e: Exception) {
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

    fun getMentors() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    if (skills.value is Resource.Success && skills.value.data != null) {
                        _mentors.emit(Resource.Loading())
                        val tmp = skills.value.data!!
                        val response = searchRepository.search(
                            tmp.filter { it.active }
                        )
                        when (response.code()) {
                            SUCCESS_CODE -> {
                                if (response.body() != null) {
                                    response.body()?.run {
                                        _mentors.emit(
                                            Resource.Success(this.users.toList())
                                        )
                                    }
                                } else {
                                    _mentors.emit(Resource.Success(listOf()))
                                }
                            }
                            else -> {
                                val body: String?
                                body = response.errorBody().toString()

                                _mentors.emit(Resource.Error(body))
                            }
                        }
                    } else {
                        _mentors.emit(Resource.Error("NoSkills"))
                    }
                } catch (e: Exception) {
                    _mentors.emit(
                        Resource.Error(
                            "Err when try search: " + e.message,
                            null
                        )
                    )
                }
            }
        }
    }
}
