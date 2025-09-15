package com.example.socialdeal.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.socialdeal.data.values.ErrorTypes
import com.example.socialdeal.ui.repositories.SettingsRepositoryInterface
import com.example.socialdeal.ui.values.CurrencyTypes
import com.example.socialdeal.utilities.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(
    val dataStore: DataStore<Preferences>
) : SettingsRepositoryInterface {

    private val currencyTypePreferenceKey = stringPreferencesKey("currencyTypePrefKey")

    override suspend fun getCurrencySettings(): Flow<Result<CurrencyTypes, ErrorTypes>> {
        return dataStore.data.map { preferences ->
            preferences[currencyTypePreferenceKey]?.let {
                Result.Success(CurrencyTypes.valueOf(it))
            } ?: Result.Failure(ErrorTypes.NOT_FOUND)
        }
    }

    override suspend fun setSetting(currencyType: CurrencyTypes) {
        dataStore.edit { preferences ->
            preferences.set(currencyTypePreferenceKey, currencyType.name)
        }
    }

    companion object {
        @Volatile
        private var instance: SettingsRepository? = null

        fun getInstance(
            dataStore: DataStore<Preferences>
        ): SettingsRepository {
            return instance ?: synchronized(this) {
                instance ?: SettingsRepository(
                    dataStore = dataStore
                ).also { instance = it }
            }
        }
    }
}