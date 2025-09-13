package com.example.socialdeal.ui.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.socialdeal.data.di.DataLayerInjectorInterface
import com.example.socialdeal.ui.MainActivityViewModelFactory

class InjectorUtils(
    private val dataLayerInjector: DataLayerInjectorInterface
) {
    fun provideMainActivityViewModelFactory(): MainActivityViewModelFactory {
        return MainActivityViewModelFactory(dataLayerInjector.provideDealsRepository())
    }

    companion object {
        @Volatile var INSTANCE: InjectorUtils? = null

        fun getInstance(dataStore: DataStore<Preferences>): InjectorUtils {
            val dataLayerInjector = com.example.socialdeal.data.di.InjectorUtils.getInstance(dataStore)
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: InjectorUtils(dataLayerInjector).also { INSTANCE = it }
            }
        }
    }
}