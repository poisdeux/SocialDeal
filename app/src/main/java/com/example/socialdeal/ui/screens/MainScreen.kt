package com.example.socialdeal.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.socialdeal.R
import com.example.socialdeal.ui.components.DealItem
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface.Deal
import com.example.socialdeal.ui.theme.SocialDealTheme
import com.example.socialdeal.ui.values.ErrorMessage

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainScreenState: MainScreenState
) {
    when (mainScreenState.state) {
        is MainScreenState.States.Loading -> LoadingScreen()
        is MainScreenState.States.CloseApp -> {}
        is MainScreenState.States.ShowListOfDeals -> DealsList(deals = mainScreenState.state.deals)
        is MainScreenState.States.ShowDealDetail -> {}
        is MainScreenState.States.ShowSettings -> {}
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
    deals: List<Deal>
) {
    LazyColumn(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_default)),
    ) {
        items(items = deals, key = { deal -> deal.id }) { deal ->
            DealItem(
                deal = deal
            )
        }
    }
}

data class MainScreenState(
    val state: States,
    val error: ErrorMessage? = null
) {
    sealed class States {
        data object Loading : States()
        data object CloseApp : States()
        data class ShowListOfDeals(val deals: List<Deal>): States()
        data class ShowDealDetail(val deal: Deal) : States()
        data object ShowSettings: States()
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    SocialDealTheme {
        MainScreen(
            mainScreenState = MainScreenState(MainScreenState.States.Loading)
        )
    }
}

