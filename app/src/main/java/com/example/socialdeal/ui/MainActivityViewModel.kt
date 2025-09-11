package com.example.socialdeal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.socialdeal.data.network.ApiClient
import com.example.socialdeal.data.repositories.DealsRepository
import com.example.socialdeal.data.utilities.Result
import com.example.socialdeal.ui.screens.MainScreenState
import com.example.socialdeal.ui.values.ErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivityViewModel(
    val dealsRepository: DealsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainScreenState(state = MainScreenState.States.Loading))
    val uiState: StateFlow<MainScreenState> = _uiState

    init {
        viewModelScope.launch {
            dealsRepository.getDeals().let { result ->
                when (result) {
                    is Result.Failure -> _uiState.update { state ->
                        state.copy(error = ErrorMessage.UNKNOWN_ERROR)
                    }
                    is Result.Success -> _uiState.update { state ->
                        state.copy(
                            error = null,
                            state = MainScreenState.States.ShowListOfDeals(result.result)
                        )
                    }
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MainActivityViewModel(DealsRepository.getInstance(ApiClient.getInstance()))
            }
        }
    }
}