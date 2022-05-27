package com.gotov.getmeapp.main.plans.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    private val _name = MutableStateFlow("")

    val name = _name.asStateFlow()

    fun sendName(text: String) {
        viewModelScope.launch {
            _name.emit(text)
        }
    }
}
