package com.anggarad.dev.foodfinder.core.domain.repository

import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.LoginResponse
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    suspend fun saveCredentials(token: String, userId: Int)
    suspend fun userLogin(email: String, password: String): Flow<ApiResponse<LoginResponse>>
}