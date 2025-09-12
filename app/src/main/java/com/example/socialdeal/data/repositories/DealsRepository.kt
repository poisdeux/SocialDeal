package com.example.socialdeal.data.repositories

import com.example.socialdeal.data.mappers.convert
import com.example.socialdeal.data.network.ApiClient
import com.example.socialdeal.data.utilities.Result
import com.example.socialdeal.data.utilities.tryWithErrorHandling
import com.example.socialdeal.data.values.ErrorTypes
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface.Deal
import com.example.socialdeal.ui.repositories.DealsRepositoryInterface.DealDescription

class DealsRepository private constructor(val apiClient: ApiClient) : DealsRepositoryInterface {

    override suspend fun getDeals(): Result<List<Deal>, ErrorTypes> {
        return tryWithErrorHandling {
            apiClient.dealsService.deals().deals?.mapNotNull {
                it.convert { deal -> deal }
            }?.let {
                Result.Success(it)
            } ?: Result.Failure(ErrorTypes.NOT_FOUND)
        }
    }

    override suspend fun getDealDescription(dealUnique: DealsRepositoryInterface.UniqueID): Result<DealDescription, ErrorTypes> {
        return tryWithErrorHandling {
            apiClient.dealsService.dealDetails(dealUnique.value).description?.let {
                Result.Success(DealDescription(it))
            } ?: Result.Failure(ErrorTypes.NOT_FOUND)
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