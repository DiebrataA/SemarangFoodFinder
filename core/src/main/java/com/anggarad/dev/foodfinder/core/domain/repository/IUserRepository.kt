package com.anggarad.dev.foodfinder.core.domain.repository

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.UserResponse
import com.anggarad.dev.foodfinder.core.domain.model.ReviewDetails
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    //    fun getUserDetail(userId: Int): Flow<Resource<UserDetail>>
    suspend fun getUserDetail(userId: Int): Flow<ApiResponse<UserResponse>>

    fun getUserId(): Flow<Int>

    fun getUsersReview(userId: Int): Flow<Resource<List<ReviewDetails>>>
}