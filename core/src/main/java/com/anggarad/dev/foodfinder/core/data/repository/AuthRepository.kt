package com.anggarad.dev.foodfinder.core.data.repository

import com.anggarad.dev.foodfinder.core.data.DataStoreManager
import com.anggarad.dev.foodfinder.core.data.source.RemoteDataSource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.LoginResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.RegisterResponse
import com.anggarad.dev.foodfinder.core.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow

class AuthRepository(
    private val dataStoreManager: DataStoreManager,
    private val remoteDataSource: RemoteDataSource,
) : IAuthRepository {
    override suspend fun saveCredentials(token: String, userId: Int) {
        return dataStoreManager.saveToDataStore(token, userId)
    }

    override suspend fun userLogin(
        email: String,
        password: String
    ): Flow<ApiResponse<LoginResponse>> {
        return remoteDataSource.userLogin(email, password)
    }

    override suspend fun userRegister(
        email: String,
        password: String,
        name: String,
        phoneNum: String,
        address: String
    ): Flow<ApiResponse<RegisterResponse>> {
        return remoteDataSource.userRegister(email, password, name, phoneNum, address)
    }

}