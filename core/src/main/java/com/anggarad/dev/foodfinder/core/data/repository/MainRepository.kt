package com.anggarad.dev.foodfinder.core.data.repository

import com.anggarad.dev.foodfinder.core.data.DataStoreManager
import com.anggarad.dev.foodfinder.core.domain.repository.IMainRepository
import kotlinx.coroutines.flow.Flow

class MainRepository(private val dataStoreManager: DataStoreManager) : IMainRepository {
    override fun checkToken(): Flow<String?> {
        return dataStoreManager.getUserToken
    }
}