package com.example.socialdeal.data.network.services

import com.example.socialdeal.data.values.JsonDealDetails
import com.example.socialdeal.data.values.JsonDeals
import retrofit2.http.GET
import retrofit2.http.Query

interface DealsService {
    @GET("deals.json")
    suspend fun deals(): JsonDeals

    @GET("details.json")
    suspend fun dealDetails(@Query("id") dealUnique: String): JsonDealDetails
}