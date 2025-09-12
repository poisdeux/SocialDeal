package com.example.socialdeal.data.mappers

import com.example.socialdeal.data.Constants
import com.example.socialdeal.data.values.JsonDeal
import com.example.socialdeal.ui.mappers.convert
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface.Deal
import java.net.URL

fun <R> JsonDeal.convert(result: (Deal) -> R) : R? {
    return this.unique?.let { uniqueID ->
        result(
            Deal(
                id = DealsRepositoryInterface.UniqueID(uniqueID),
                title = this.title ?: "",
                company = this.company ?: "",
                city = this.city ?: "",
                imageUrl = this.image?.let { URL(Constants.IMAGE_URL_PREFIX + this.image) },
                originalPrice = this.prices?.fromPrice?.convert { it },
                discountedPrice = this.prices?.price?.convert { it },
                sold = this.soldLabel ?: "",
                currencySymbol = this.prices?.price?.currency?.symbol ?: ""
            )
        )
    }
}