package com.example.socialdeal.ui.repositories

import com.example.socialdeal.data.utilities.Result
import com.example.socialdeal.data.values.ErrorTypes
import com.example.socialdeal.ui.values.Price
import java.net.URL

interface DealsRepositoryInterface {
    suspend fun getDeals(): Result<List<Deal>, ErrorTypes>

    data class Deal(
        val id: String,
        val imageUrl: URL?,
        val title: String,
        val company: String,
        val city: String,
        val sold: String,
        val currencySymbol: String,
        val originalPrice: Price?,
        val discountedPrice: Price?
    )
}