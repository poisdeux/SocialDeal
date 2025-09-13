package com.example.socialdeal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.socialdeal.data.repositories.DealsRepository

class MainActivityViewModelFactory(
    val dealsRepository: DealsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(
                dealsRepository = dealsRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}