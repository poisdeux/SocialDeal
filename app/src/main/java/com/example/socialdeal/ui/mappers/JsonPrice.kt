package com.example.socialdeal.ui.mappers

import com.example.socialdeal.data.values.JsonPrice
import com.example.socialdeal.ui.values.Price

fun <R> JsonPrice.convert(result: (Price) -> R?) : R? {
    return this.amount?.toBigInteger()?.divideAndRemainder(100.toBigInteger())?.let { integerAndDecimalPair ->
        result(
            Price(
                integerValue = integerAndDecimalPair[0],
                decimalValue = integerAndDecimalPair[1],
            )
        )
    }
}