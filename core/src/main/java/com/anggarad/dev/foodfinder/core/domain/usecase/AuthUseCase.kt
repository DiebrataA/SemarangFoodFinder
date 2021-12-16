package com.anggarad.dev.foodfinder.core.domain.usecase

import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {

    suspend fun saveCredential(token: String, userId: Int)
    suspend fun userLogin(email: String, password: String): Flow<ApiResponse<LoginResponse>>
}