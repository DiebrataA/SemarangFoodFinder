package com.anggarad.dev.foodfinder.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "USER_DATASTORE")

    companion object {
        val TOKEN = stringPreferencesKey("USER_TOKEN")
        val USER_ID = intPreferencesKey("USER_ID")
    }

    suspend fun saveToDataStore(token: String, userId: Int) {
        context.dataStore.edit {
            it[TOKEN] = token
            it[USER_ID] = userId
        }
    }

    val getUserToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[TOKEN] ?: ""
        }

    val getUserId: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ID] ?: 0
        }
}

