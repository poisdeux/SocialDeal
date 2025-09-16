package com.example.socialdeal.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface
import com.example.socialdeal.ui.screens.MainScreenAction
import com.example.socialdeal.ui.theme.SocialDealTheme
import com.example.socialdeal.ui.values.CurrencyTypes
import com.example.socialdeal.ui.values.Price
import java.net.URL
import kotlin.random.Random

@Composable
fun DealsList(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    deals: List<DealsRepositoryInterface.Deal>,
    currencySetting: CurrencyTypes,
    onAction: (MainScreenAction) -> Unit
) {
    if (deals.isEmpty()) {
        LoadingIndicator(modifier)
    } else {
        LazyColumn(
            modifier = modifier,
            state = lazyListState
        ) {
            items(items = deals, key = { deal -> deal.id.value }) { deal ->
                DealItem(
                    modifier = Modifier.clickable {
                        onAction(
                            MainScreenAction.ShowDealDetail(
                                deal
                            )
                        )
                    },
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

@Preview(showBackground = true)
@Composable
private fun DealsListPreview() {
    val deals = MutableList(30) { index ->
        DealsRepositoryInterface.Deal(
            id = DealsRepositoryInterface.UniqueID("${index}"),
            imageUrl = URL("https://via.placeholder.com/150"),
            title = "Deal ${index}",
            company = "Company ${index}",
            city = "City ${index}",
            sold = "Verkocht: ${Random.nextInt(500)}",
            isFavourite = Random.nextBoolean(),
            originalPrice = Price(Random.nextInt(2000).toBigInteger(), Random.nextInt(100).toBigInteger()),
            discountedPrice = Price(Random.nextInt(1000).toBigInteger(), Random.nextInt(100).toBigInteger())
        )
    }
    SocialDealTheme {
        DealsList(
            lazyListState = LazyListState(),
            deals = deals,
            currencySetting = CurrencyTypes.EURO,
            onAction = {}
        )
    }
}

