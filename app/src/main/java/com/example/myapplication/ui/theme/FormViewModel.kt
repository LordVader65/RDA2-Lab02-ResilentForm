package com.example.myapplication.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.UserPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FormViewModel(
    private val stateHandle: SavedStateHandle,
    private val userPrefs: UserPreferences
) : ViewModel() {

    var indicador by mutableStateOf(false)
        private set
    var email by mutableStateOf(stateHandle.get<String>("email_key") ?: "")
        private set

    fun updateEmail(newEmail: String) {
        email = newEmail
        stateHandle["email_key"] = newEmail
    }

    val nameFromDisk = userPrefs.userName.asLiveData()

    fun saveName(newName: String) {
        viewModelScope.launch {
            userPrefs.saveName(newName)

            indicador = true
            delay(1000)
            indicador = false
        }
    }
}