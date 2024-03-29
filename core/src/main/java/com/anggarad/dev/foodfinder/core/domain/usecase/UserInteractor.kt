package com.anggarad.dev.foodfinder.core.domain.usecase

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.ReviewDetails
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import com.anggarad.dev.foodfinder.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserInteractor(private val userRepository: IUserRepository) : UserUseCase {


//    override fun getUserDetail(userId: Int): Flow<Resource<UserDetail>> {
//        return userRepository.getUserDetail(userId)
//    }

    override fun getUserDetail(userId: Int): Flow<Resource<UserDetail>> {
        return userRepository.getUserDetail(userId)
    }

    override fun getUserId(): Flow<Int> {
        return userRepository.getUserId()
    }

    override fun getUsersReview(userId: Int): Flow<Resource<List<ReviewDetails>>> {
        return userRepository.getUsersReview(userId)
    }
}