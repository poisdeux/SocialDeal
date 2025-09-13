package com.example.socialdeal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialdeal.ui.components.TabBarItem
import com.example.socialdeal.ui.mappers.convert
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface
import com.example.socialdeal.ui.screens.MainScreenState
import com.example.socialdeal.ui.values.ErrorMessageType
import com.example.socialdeal.utilities.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivityViewModel(
    val dealsRepository: DealsRepositoryInterface
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainScreenState(state = MainScreenState.States.ShowListOfDeals))
    val uiState: StateFlow<MainScreenState> = _uiState

    private val _deals = MutableStateFlow<Result<List<DealsRepositoryInterface.Deal>, ErrorMessageType>>(Result.Success(emptyList()))
    val deals: StateFlow<Result<List<DealsRepositoryInterface.Deal>, ErrorMessageType>> = _deals

    private var currentTabBarItem: TabBarItem = TabBarItem.DEALS

    init {
        viewModelScope.launch {
            dealsRepository.getDeals(externalScope = viewModelScope).collect { result ->
                _deals.emit(
                    value = when (result) {
                        is Result.Failure -> Result.Failure(result.error.convert { it })
                        is Result.Success -> Result.Success(result.result)
                    }
                )
            }
        }
    }

    fun onBackPressed() {
        when (uiState.value.state) {
            MainScreenState.States.CloseApp,
            MainScreenState.States.ShowFavourites,
            is MainScreenState.States.ShowListOfDeals,
            MainScreenState.States.ShowSettings -> _uiState.update { state ->
                state.copy(state = MainScreenState.States.CloseApp)
            }
            is MainScreenState.States.ShowDealDetail -> {
                when (currentTabBarItem) {
                    TabBarItem.DEALS -> showDeals()
                    TabBarItem.FAVOURITES -> showFavourites()
                    TabBarItem.SETTINGS -> showSettings()
                }
            }
        }
    }

    fun openTab(tabBarItem: TabBarItem) {
        currentTabBarItem = tabBarItem
        when (tabBarItem) {
            TabBarItem.DEALS -> showDeals()
            TabBarItem.FAVOURITES -> showFavourites()
            TabBarItem.SETTINGS -> showSettings()
        }
    }

    fun showDealDetail(deal: DealsRepositoryInterface.Deal) {
        viewModelScope.launch {
            dealsRepository.getDealDescription(deal.id).let { result ->
                _uiState.update { state ->
                    when (result) {
                        is Result.Failure -> state.copy(error = ErrorMessageType.UNKNOWN_ERROR)
                        is Result.Success -> state.copy(
                            error = null,
                            state = MainScreenState.States.ShowDealDetail(deal, result.result)
                        )
                    }
                }
            }
        }
    }

    fun onFavouriteIconClicked(deal: DealsRepositoryInterface.Deal, isFavourite: Boolean) {
        viewModelScope.launch {
            dealsRepository.updateDeal(deal, isFavourite).let { result ->
                when (result) {
                    is Result.Failure -> _uiState.update { state -> state.copy(error = ErrorMessageType.UNKNOWN_ERROR) }
                    is Result.Success -> (uiState.value.state as? MainScreenState.States.ShowDealDetail)?.let { showDealDetailState ->
                        _uiState.update { state ->
                            state.copy(
                                error = null,
                                state = showDealDetailState.copy(deal = result.result)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun showDeals() {
        _uiState.update { state ->
            state.copy(
                error = null,
                state = MainScreenState.States.ShowListOfDeals
            )
        }
    }

    private fun showFavourites() {
        _uiState.update { state ->
            state.copy(error = null, state = MainScreenState.States.ShowFavourites)
        }
    }

    private fun showSettings() {
        _uiState.update { state ->
            state.copy(error = null, state = MainScreenState.States.ShowSettings)
        }
    }
}