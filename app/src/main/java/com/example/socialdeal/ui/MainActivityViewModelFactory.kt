package com.example.socialdeal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface
import com.example.socialdeal.ui.repositories.SettingsRepositoryInterface

class MainActivityViewModelFactory(
    val dealsRepository: DealsRepositoryInterface,
    val settingsRepository: SettingsRepositoryInterface
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(
                dealsRepository = dealsRepository,
                settingsRepository = settingsRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}