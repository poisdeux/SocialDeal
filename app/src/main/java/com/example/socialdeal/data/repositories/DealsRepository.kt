package com.example.socialdeal.data.repositories

import com.example.socialdeal.data.mappers.convert
import com.example.socialdeal.data.network.ApiClient
import com.example.socialdeal.data.utilities.Result
import com.example.socialdeal.data.utilities.tryWithErrorHandling
import com.example.socialdeal.data.values.ErrorTypes
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface.Deal

class DealsRepository private constructor(val apiClient: ApiClient) : DealsRepositoryInterface {

    override suspend fun getDeals(): Result<List<Deal>, ErrorTypes> {
        return tryWithErrorHandling {
            apiClient.dealsService.deals().deals?.map {
                it.convert { deal -> deal }
            } ?: emptyList()
        }
    }

    companion object {
        @Volatile
        private var instance: DealsRepository? = null

        fun getInstance(apiClient: ApiClient): DealsRepository {
            return instance ?: synchronized(this) {
                instance ?: DealsRepository(apiClient).also { instance = it }
            }
        }
    }
}