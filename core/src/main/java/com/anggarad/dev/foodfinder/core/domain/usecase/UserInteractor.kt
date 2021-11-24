package com.anggarad.dev.foodfinder.core.domain.usecase

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.LoginResponse
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import com.anggarad.dev.foodfinder.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserInteractor(private val userRepository: IUserRepository) : UserUseCase {
    override suspend fun saveCredential( token: String) {
        return userRepository.saveCredentials( token)
    }




    override suspend fun userLogin(email:String, password: String): Flow<ApiResponse<LoginResponse>> {
        return userRepository.userLogin(email, password)
    }
}