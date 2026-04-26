package com.example.myapplication.ui.theme

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.myapplication.data.UserPreferences

class FormViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val userPrefs: UserPreferences
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(FormViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FormViewModel(handle, userPrefs) as T
        }

        throw IllegalArgumentException("ViewModel desconocido")
    }
}