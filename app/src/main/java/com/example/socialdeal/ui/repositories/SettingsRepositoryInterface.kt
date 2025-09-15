package com.example.socialdeal.ui.repositories

import com.example.socialdeal.data.values.ErrorTypes
import com.example.socialdeal.ui.values.CurrencyTypes
import com.example.socialdeal.utilities.Result
import kotlinx.coroutines.flow.Flow

interface SettingsRepositoryInterface {
    suspend fun getCurrencySettings(): Flow<Result<CurrencyTypes, ErrorTypes>>
    suspend fun setSetting(currencyType: CurrencyTypes)
}