package com.example.socialdeal.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.socialdeal.R
import com.example.socialdeal.ui.components.DealItem
import com.example.socialdeal.ui.components.DealItemAction
import com.example.socialdeal.ui.components.ItemSelector
import com.example.socialdeal.ui.mappers.convert
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface.Deal
import com.example.socialdeal.ui.theme.SocialDealTheme
import com.example.socialdeal.ui.theme.TextStyles
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
                modifier = modifier,
                errorMessageType = deals.error
            )
            is Result.Success -> DealsList(
                modifier = modifier,
                lazyListState = dealsListState,
                deals = deals.result,
                currencySetting = currencySetting,
                onAction = onAction
            )
        }
        is MainScreenState.States.ShowDealDetail -> DealDetail(
            modifier = modifier,
            deal = mainScreenState.state.deal,
            description = mainScreenState.state.description,
            currencySetting = currencySetting,
            onAction = onAction
        )
        is MainScreenState.States.ShowSettings -> Settings(
            modifier = modifier,
            currencySetting = currencySetting,
            onAction = onAction
        )
        MainScreenState.States.ShowFavourites -> when (deals) {
            is Result.Failure -> ErrorMessage(
                modifier = modifier,
                errorMessageType = deals.error
            )
            is Result.Success -> DealsList (
                modifier = modifier,
                lazyListState = favoritesListState,
                deals = deals.result.filter { it.isFavourite },
                currencySetting = currencySetting,
                onAction = onAction
            )
        }
    }
}

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    errorMessageType: ErrorMessageType
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(errorMessageType.stringResourceId),
            style = TextStyles.default
        )
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun DealsList(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    deals: List<Deal>,
    currencySetting: CurrencyTypes,
    onAction: (MainScreenAction) -> Unit
) {
    if (deals.isEmpty()) {
        LoadingScreen(modifier)
    } else {
        LazyColumn(
            modifier = modifier.padding(dimensionResource(R.dimen.padding_default)),
            state = lazyListState
        ) {
            items(items = deals, key = { deal -> deal.id.value }) { deal ->
                DealItem(
                    modifier = Modifier.clickable { onAction(MainScreenAction.ShowDealDetail(deal)) },
                    deal = deal,
                    currencySetting = currencySetting,
                    onAction = {
                        when (it) {
                            is DealItemAction.IsFavourite -> {
                                onAction(
                                    MainScreenAction.OnFavouriteIconClicked(
                                        deal,
                                        it.isFavourite
                                    )
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun DealDetail(
    modifier: Modifier = Modifier,
    deal: Deal,
    description: DealsRepositoryInterface.DealDescription,
    currencySetting: CurrencyTypes,
    onAction: (MainScreenAction) -> Unit
) {
    DealItem(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_default))
            .verticalScroll(rememberScrollState()),
        deal = deal,
        description = description,
        currencySetting = currencySetting,
        onAction = {
            when (it) {
                is DealItemAction.IsFavourite -> {
                    onAction(MainScreenAction.OnFavouriteIconClicked(deal, it.isFavourite))
                }
            }
        }
    )
}

@Composable
fun Settings(
    modifier: Modifier,
    currencySetting: CurrencyTypes,
    onAction: (MainScreenAction) -> Unit
) {
    val currencyTypesStringHashMap = mutableMapOf<String, CurrencyTypes>()
    CurrencyTypes.entries.forEach {
        currencyTypesStringHashMap.put(stringResource(it.convert { it.value }), it)
    }

    ItemSelector(
        modifier = modifier,
        label = stringResource(R.string.currency),
        selectedItem = stringResource(currencySetting.convert { it.value }),
        items = CurrencyTypes.entries.map { stringResource(it.convert { it.value }) }
    ) { itemSelected ->
        currencyTypesStringHashMap[itemSelected]?.let {
            onAction(MainScreenAction.CurrencySettingChanged(it))
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

