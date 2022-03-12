package com.anggarad.dev.foodfinder.core.domain.repository

import androidx.lifecycle.LiveData
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import com.anggarad.dev.foodfinder.core.domain.model.UserReviewDetails
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    //    fun getUserDetail(userId: Int): Flow<Resource<UserDetail>>
    fun getUserDetail(userId: Int): Flow<Resource<UserDetail>>

    fun getUserId(): Flow<Int>

    fun getUsersReview(userId: Int): Flow<Resource<List<UserReviewDetails>>>

    fun fetchUser(userId: String): LiveData<Resource<UserDetail>>
}