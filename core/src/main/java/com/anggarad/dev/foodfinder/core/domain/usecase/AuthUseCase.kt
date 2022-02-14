package com.anggarad.dev.foodfinder.core.domain.usecase

import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.CurrUserItem
import com.anggarad.dev.foodfinder.core.data.source.remote.response.LoginResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {

    suspend fun saveCredential(token: String, userId: Int)
    suspend fun saveUserInfo(userDetail: CurrUserItem)
    suspend fun userLogin(email: String, password: String): Flow<ApiResponse<LoginResponse>>
    suspend fun userRegister(
        email: String,
        password: String,
        name: String,
        phoneNum: String,
        address: String
    ): Flow<ApiResponse<RegisterResponse>>
}