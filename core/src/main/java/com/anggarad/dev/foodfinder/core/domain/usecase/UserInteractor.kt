package com.anggarad.dev.foodfinder.core.domain.usecase

import androidx.lifecycle.LiveData
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import com.anggarad.dev.foodfinder.core.domain.model.UserReviewDetails
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

    override fun getUsersReview(userId: Int): Flow<Resource<List<UserReviewDetails>>> {
        return userRepository.getUsersReview(userId)
    }

    override fun fetchUser(userId: String): LiveData<Resource<UserDetail>> {
        return userRepository.fetchUser(userId)
    }
}