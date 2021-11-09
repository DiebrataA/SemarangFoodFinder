package com.anggarad.dev.foodfinder.core.domain.usecase

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import com.anggarad.dev.foodfinder.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserInteractor(private val userRepository: IUserRepository) : UserUseCase {
    override fun getUserDetail(): Flow<Resource<UserDetail>> {
        return userRepository.getUserDetail()
    }

    override fun userLogin(): Flow<Resource<UserDetail>> {
        return userRepository.userLogin()
    }
}