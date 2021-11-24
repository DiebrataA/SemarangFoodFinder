package com.anggarad.dev.foodfinder.core.data.repository

import com.anggarad.dev.foodfinder.core.data.DataStoreManager
import com.anggarad.dev.foodfinder.core.data.source.RemoteDataSource
import com.anggarad.dev.foodfinder.core.data.source.local.LocalDataSource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.LoginResponse
import com.anggarad.dev.foodfinder.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class AuthRepository(
    private val dataStoreManager: DataStoreManager,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IUserRepository {
    override suspend fun saveCredentials(token: String) {
        return dataStoreManager.saveToDataStore(token)
    }



    override suspend fun userLogin(
        email: String,
        password: String
    ): Flow<ApiResponse<LoginResponse>> {
        return remoteDataSource.userLogin(email, password)
    }

}