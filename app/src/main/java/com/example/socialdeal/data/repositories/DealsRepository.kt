package com.example.socialdeal.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.socialdeal.data.mappers.convert
import com.example.socialdeal.data.network.ApiClient
import com.example.socialdeal.data.utilities.tryWithErrorHandling
import com.example.socialdeal.data.values.ErrorTypes
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface.Deal
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface.DealDescription
import com.example.socialdeal.utilities.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DealsRepository private constructor(
    val apiClient: ApiClient,
    val dataStore: DataStore<Preferences>,
) : DealsRepositoryInterface {

    private val deals = MutableStateFlow<Result<List<Deal>, ErrorTypes>>(
        Result.Success(emptyList())
    )

    override suspend fun getDeals(externalScope: CoroutineScope): StateFlow<Result<List<Deal>, ErrorTypes>> {
        externalScope.launch(Dispatchers.IO) {
            deals.emit(
                tryWithErrorHandling {
                    apiClient.dealsService.deals().deals?.mapNotNull { jsonDeal ->
                        jsonDeal.convert { it }?.let { deal ->
                            val isFavorite = dataStore.data.map { preferences ->
                                preferences[booleanPreferencesKey(deal.id.value)]
                            }.first() ?: false
                            deal.copy(isFavourite = isFavorite)
                        }
                    }?.let {
                        Result.Success(it)
                    } ?: Result.Failure(ErrorTypes.NOT_FOUND)
                }
            )
        }
        return deals
    }

    override suspend fun getDealDescription(dealUnique: DealsRepositoryInterface.UniqueID): Result<DealDescription, ErrorTypes> {
        return tryWithErrorHandling {
            apiClient.dealsService.dealDetails(dealUnique.value).description?.let {
                Result.Success(DealDescription(it))
            } ?: Result.Failure(ErrorTypes.NOT_FOUND)
        }
    }

    override suspend fun updateDeal(deal: Deal, isFavourite: Boolean): Result<Deal, ErrorTypes> {
        dataStore.edit { preferences -> preferences[booleanPreferencesKey(deal.id.value)] = isFavourite }
        val updatedDeal = deal.copy(isFavourite = isFavourite)
        deals.value.let { result ->
            (result as? Result.Success)?.result?.let { dealsList ->
                val newList = dealsList.toMutableList().also {
                    it[dealsList.indexOfFirst { it.id.value == deal.id.value }] = updatedDeal
                }
                Result.Success(newList.toList())
            } ?: result
        }.let {
            deals.emit(it)
        }

        return Result.Success(updatedDeal)
    }

    companion object {
        @Volatile
        private var instance: DealsRepository? = null

        fun getInstance(
            apiClient: ApiClient,
            dataStore: DataStore<Preferences>
        ): DealsRepository {
            return instance ?: synchronized(this) {
                instance ?: DealsRepository(
                    apiClient = apiClient,
                    dataStore = dataStore
                ).also { instance = it }
            }
        }
    }
}