package com.anggarad.dev.foodfinder.core.domain.usecase

import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.UserResponse
import com.anggarad.dev.foodfinder.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserInteractor(private val userRepository: IUserRepository) : UserUseCase {


//    override fun getUserDetail(userId: Int): Flow<Resource<UserDetail>> {
//        return userRepository.getUserDetail(userId)
//    }

    override suspend fun getUserDetail(userId: Int): Flow<ApiResponse<UserResponse>> {
        return userRepository.getUserDetail(userId)
    }

    override fun getUserId(): Flow<Int> {
        return userRepository.getUserId()
    }
}