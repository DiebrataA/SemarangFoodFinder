package com.anggarad.dev.foodfinder.core.domain.usecase

import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.LoginResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.RegisterResponse
import com.anggarad.dev.foodfinder.core.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow

class AuthInteractor(private val authRepos: IAuthRepository) : AuthUseCase {
    override suspend fun saveCredential(token: String, userId: Int) {
        return authRepos.saveCredentials(token, userId)
    }

    override suspend fun userLogin(
        email: String,
        password: String
    ): Flow<ApiResponse<LoginResponse>> {
        return authRepos.userLogin(email, password)
    }

    override suspend fun userRegister(
        email: String,
        password: String,
        name: String,
        phoneNum: String,
        address: String
    ): Flow<ApiResponse<RegisterResponse>> {
        return authRepos.userRegister(email, password, name, phoneNum, address)
    }
}