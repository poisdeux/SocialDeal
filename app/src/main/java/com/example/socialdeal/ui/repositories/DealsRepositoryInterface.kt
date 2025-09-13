package com.example.socialdeal.ui.repositories

import com.example.socialdeal.data.values.ErrorTypes
import com.example.socialdeal.ui.values.Price
import com.example.socialdeal.utilities.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import java.net.URL

interface DealsRepositoryInterface {
    suspend fun getDeals(externalScope: CoroutineScope): StateFlow<Result<List<Deal>, ErrorTypes>>
    suspend fun getDealDescription(dealUnique: UniqueID): Result<DealDescription, ErrorTypes>

    suspend fun updateDeal(deal: Deal, isFavourite: Boolean): Result<Deal, ErrorTypes>

    @JvmInline
    value class UniqueID(val value: String)

    @JvmInline
    value class DealDescription(val value: String)

    data class Deal(
        val id: UniqueID,
        val imageUrl: URL?,
        val title: String,
        val company: String,
        val city: String,
        val sold: String,
        val currencySymbol: String,
        val originalPrice: Price?,
        val discountedPrice: Price?,
        val isFavourite: Boolean
    )
}