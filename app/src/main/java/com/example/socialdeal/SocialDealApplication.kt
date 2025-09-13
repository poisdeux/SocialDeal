package com.example.socialdeal

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.socialdeal.ui.di.InjectorUtils

class SocialDealApplication : Application() {
    val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate() {
        super.onCreate()
        InjectorUtils.getInstance(dataStore)
    }
}