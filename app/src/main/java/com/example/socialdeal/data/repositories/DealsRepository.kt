package com.example.socialdeal.data.repositories

import com.example.socialdeal.data.network.ApiClient
import com.example.socialdeal.data.utilities.Result
import com.example.socialdeal.data.utilities.tryWithErrorHandling
import com.example.socialdeal.data.values.ErrorTypes
import com.example.socialdeal.data.values.JsonDeals

class DealsRepository private constructor(val apiClient: ApiClient) {

    suspend fun getDeals(): Result<JsonDeals, ErrorTypes> {
        return tryWithErrorHandling {
            apiClient.dealsService.deals()
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