package com.example.socialdeal.ui.mappers

import androidx.annotation.StringRes
import com.example.socialdeal.R
import com.example.socialdeal.ui.values.CurrencyTypes

@JvmInline
value class StringResource(@field:StringRes val value: Int)

fun <R> CurrencyTypes.convert(result: (StringResource) -> R): R {
    return result(
        StringResource(when (this) {
            CurrencyTypes.EURO -> R.string.euro
            CurrencyTypes.DOLLAR -> R.string.dollar
        })
    )
}