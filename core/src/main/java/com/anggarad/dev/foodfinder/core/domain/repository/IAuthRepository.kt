package com.anggarad.dev.foodfinder.core.domain.repository

import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.LoginResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    suspend fun saveCredentials(token: String, userId: Int)
    suspend fun userLogin(email: String, password: String): Flow<ApiResponse<LoginResponse>>
    suspend fun userRegister(
        email: String,
        password: String,
        name: String,
        phoneNum: String,
        address: String
    ): Flow<ApiResponse<RegisterResponse>>
}