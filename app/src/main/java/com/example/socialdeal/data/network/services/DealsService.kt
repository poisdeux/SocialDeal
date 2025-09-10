package com.example.socialdeal.data.network.services

import com.example.socialdeal.data.values.JsonDeals
import retrofit2.http.GET

interface DealsService {
    @GET("deals.json")
    suspend fun deals(): JsonDeals
}