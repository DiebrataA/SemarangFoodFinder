package com.anggarad.dev.foodfinder.core.domain.usecase

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.ReviewDetails
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    //    fun getUserDetail(userId: Int): Flow<Resource<UserDetail>>
    fun getUserDetail(userId: Int): Flow<Resource<UserDetail>>

    fun getUserId(): Flow<Int>

    fun getUsersReview(userId: Int): Flow<Resource<List<ReviewDetails>>>
}