package com.anggarad.dev.foodfinder.core.domain.repository

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.PostReviewResponse
import com.anggarad.dev.foodfinder.core.domain.model.ReviewDetails
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface IReviewRepository {

    fun getRestoReviews(restoId: Int): Flow<Resource<List<ReviewDetails>>>
    suspend fun postReview(
        token: String,
        restoId: Int,
        userId: Int,
        rating: Float,
        comments: String,
        fileName: String,
        body: RequestBody?
    ): Flow<ApiResponse<PostReviewResponse>>

    fun getToken(): Flow<String>

//    fun getUsersReview(userId: Int): Flow<Resource<List<ReviewDetails>>>
}