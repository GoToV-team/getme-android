package com.gotov.getmeapp.main.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotov.getmeapp.app.preference.AppPreferences
import com.gotov.getmeapp.main.search.model.data.Skill
import com.gotov.getmeapp.main.search.model.data.User
import com.gotov.getmeapp.main.search.model.repository.SearchRepository
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

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel(), KoinComponent {

    private val appPreferences by inject<AppPreferences>()

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
            val tmpSkills = appPreferences.getHashSet(AppPreferences.Skills)
            if (tmpSkills != null && tmpSkills.isNotEmpty()) {
                val res: MutableList<Skill> = ArrayList()
                for (skill in tmpSkills) {
                    res.add(Skill(skill, false))
                }
                _skills.emit(Resource.Success(res))
                return@launch
            }

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
                                appPreferences.putHashSet(
                                    AppPreferences.Skills,
                                    HashSet(it.skills.map { skill -> skill.name })
                                )
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
                                _mentors.emit(
                                    Resource.Error(
                                        getResponseError(response.errorBody())
                                    )
                                )
                            }
                        }
                    }
                } catch (e: IOException) {
                    _mentors.emit(
                        Resource.Error(
                            "Err when try search: " + e.message,
                            null
                        )
                    )
                } catch (e: HttpException) {
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
