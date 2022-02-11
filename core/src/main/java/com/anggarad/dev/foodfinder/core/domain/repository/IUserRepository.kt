package com.anggarad.dev.foodfinder.core.domain.repository

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.ReviewDetails
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    //    fun getUserDetail(userId: Int): Flow<Resource<UserDetail>>
    fun getUserDetail(userId: Int): Flow<Resource<UserDetail>>

    fun getUserId(): Flow<Int>

    fun getUsersReview(userId: Int): Flow<Resource<List<ReviewDetails>>>
}