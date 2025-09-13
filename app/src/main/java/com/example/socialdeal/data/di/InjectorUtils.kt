package com.example.socialdeal.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.socialdeal.data.network.ApiClient
import com.example.socialdeal.data.repositories.DealsRepository

class InjectorUtils(
    private val dataStore: DataStore<Preferences>
) : DataLayerInjectorInterface {
    override fun provideApiClient(): ApiClient {
        return ApiClient.getInstance()
    }

    override fun provideDealsRepository(): DealsRepository {
        return DealsRepository.getInstance(
            apiClient = provideApiClient(),
            dataStore = dataStore
        )
    }

    companion object {
        @Volatile var INSTANCE: InjectorUtils? = null

        fun getInstance(dataStore: DataStore<Preferences>): InjectorUtils {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: InjectorUtils(dataStore).also { INSTANCE = it }
            }
        }
    }
}