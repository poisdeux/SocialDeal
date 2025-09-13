package com.example.socialdeal.data.di

import com.example.socialdeal.data.network.ApiClient
import com.example.socialdeal.data.repositories.DealsRepository

interface DataLayerInjectorInterface {
    fun provideApiClient(): ApiClient
    fun provideDealsRepository(): DealsRepository
}