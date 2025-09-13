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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.socialdeal.R
import com.example.socialdeal.ui.components.DealItem
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface.Deal
import com.example.socialdeal.ui.theme.SocialDealTheme
import com.example.socialdeal.ui.values.ErrorMessage

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainScreenState: MainScreenState,
    onAction: (MainScreenAction) -> Unit
) {
    val listState = rememberLazyListState()

    when (mainScreenState.state) {
        is MainScreenState.States.Loading -> LoadingScreen(modifier)
        is MainScreenState.States.CloseApp -> onAction(MainScreenAction.CloseApp)
        is MainScreenState.States.ShowListOfDeals -> DealsList(
            modifier = modifier,
            lazyListState = listState,
            deals = mainScreenState.state.deals,
            onAction = onAction
        )
        is MainScreenState.States.ShowDealDetail -> DealDetail(
            modifier = modifier,
            deal = mainScreenState.state.deal,
            description = mainScreenState.state.description
        )
        is MainScreenState.States.ShowSettings -> {}
        MainScreenState.States.ShowFavourites -> {}
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = modifier
        )
    }
}

@Composable
fun DealsList(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    deals: List<Deal>,
    onAction: (MainScreenAction) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_default)),
        state = lazyListState
    ) {
        items(items = deals, key = { deal -> deal.id.value }) { deal ->
            DealItem(
                modifier = Modifier.clickable { onAction(MainScreenAction.ShowDealDetail(deal)) },
                deal = deal
            )
        }
    }
}

@Composable
fun DealDetail(
    modifier: Modifier = Modifier,
    deal: Deal,
    description: DealsRepositoryInterface.DealDescription
) {
    DealItem(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_default))
            .verticalScroll(rememberScrollState()),
        deal = deal,
        description = description
    )
}

sealed class MainScreenAction {
    data class ShowDealDetail(val deal: Deal) : MainScreenAction()
    object CloseApp : MainScreenAction()
}

data class MainScreenState(
    val state: States,
    val error: ErrorMessage? = null
) {
    sealed class States {
        data object Loading : States()
        data object CloseApp : States()
        data class ShowListOfDeals(val deals: List<Deal>): States()
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
            mainScreenState = MainScreenState(MainScreenState.States.Loading),
            onAction = {}
        )
    }
}

