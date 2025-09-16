package com.example.socialdeal.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.socialdeal.R
import com.example.socialdeal.ui.components.DealItem
import com.example.socialdeal.ui.components.DealItemAction
import com.example.socialdeal.ui.components.DealsList
import com.example.socialdeal.ui.components.ErrorMessage
import com.example.socialdeal.ui.components.Settings
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface.Deal
import com.example.socialdeal.ui.theme.SocialDealTheme
import com.example.socialdeal.ui.values.CurrencyTypes
import com.example.socialdeal.ui.values.ErrorMessageType
import com.example.socialdeal.utilities.Result

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainScreenState: MainScreenState,
    currencySetting: CurrencyTypes,
    deals: Result<List<Deal>, ErrorMessageType>,
    onAction: (MainScreenAction) -> Unit
) {
    val dealsListState = rememberLazyListState()
    val favoritesListState = rememberLazyListState()

    when (mainScreenState.state) {
        is MainScreenState.States.CloseApp -> onAction(MainScreenAction.CloseApp)
        is MainScreenState.States.ShowListOfDeals -> when (deals) {
            is Result.Failure -> ErrorMessage(
                modifier = modifier.fillMaxSize(),
                errorMessageType = deals.error
            )
            is Result.Success -> DealsList(
                modifier = modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_default)),
                lazyListState = dealsListState,
                deals = deals.result,
                currencySetting = currencySetting,
                onAction = onAction
            )
        }
        is MainScreenState.States.ShowDealDetail -> DealItem(
            modifier = modifier.padding(dimensionResource(R.dimen.padding_default)),
            deal = mainScreenState.state.deal,
            description = mainScreenState.state.description,
            currencySetting = currencySetting,
            onAction = {
                when (it) {
                    is DealItemAction.IsFavourite -> {
                        onAction(MainScreenAction.OnFavouriteIconClicked(
                            deal = mainScreenState.state.deal,
                            isFavourite = it.isFavourite
                        ))
                    }
                }
            }
        )
        is MainScreenState.States.ShowSettings -> Settings(
            modifier = modifier,
            currencySetting = currencySetting,
            onAction = onAction
        )
        MainScreenState.States.ShowFavourites -> when (deals) {
            is Result.Failure -> ErrorMessage(
                modifier = modifier.fillMaxSize(),
                errorMessageType = deals.error
            )
            is Result.Success -> DealsList(
                modifier = modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_default)),
                lazyListState = favoritesListState,
                deals = deals.result.filter { it.isFavourite },
                currencySetting = currencySetting,
                onAction = onAction
            )
        }
    }
}

sealed class MainScreenAction {
    data class CurrencySettingChanged(val currencySetting: CurrencyTypes) : MainScreenAction()
    data class ShowDealDetail(val deal: Deal) : MainScreenAction()
    data class OnFavouriteIconClicked(val deal: Deal, val isFavourite: Boolean) : MainScreenAction()
    object CloseApp : MainScreenAction()
}

data class MainScreenState(
    val state: States,
    val error: ErrorMessageType? = null
) {
    sealed class States {
        data object CloseApp : States()
        data object ShowListOfDeals: States()
        data class ShowDealDetail(
            val deal: Deal,
            val description: DealsRepositoryInterface.DealDescription
        ) : States()
        data object ShowFavourites: States()
        data object ShowSettings: States()
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    SocialDealTheme {
        MainScreen(
            mainScreenState = MainScreenState(MainScreenState.States.ShowListOfDeals),
            deals = Result.Success(emptyList()),
            currencySetting = CurrencyTypes.EURO,
            onAction = {}
        )
    }
}

