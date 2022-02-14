package com.anggarad.dev.foodfinder.core.data.repository

import com.anggarad.dev.foodfinder.core.data.DataStoreManager
import com.anggarad.dev.foodfinder.core.data.source.RemoteDataSource
import com.anggarad.dev.foodfinder.core.data.source.local.LocalDataSource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.CurrUserItem
import com.anggarad.dev.foodfinder.core.data.source.remote.response.LoginResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.RegisterResponse
import com.anggarad.dev.foodfinder.core.domain.repository.IAuthRepository
import com.anggarad.dev.foodfinder.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow

class AuthRepository(
    private val dataStoreManager: DataStoreManager,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IAuthRepository {
    override suspend fun saveCredentials(token: String, userId: Int) {
        return dataStoreManager.saveToDataStore(token, userId)
    }

    override suspend fun saveUserInfo(userDetail: CurrUserItem) {
        val userData = DataMapper.mapUserDataLoginToEntity(userDetail)
        return localDataSource.insertUserData(userData)
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