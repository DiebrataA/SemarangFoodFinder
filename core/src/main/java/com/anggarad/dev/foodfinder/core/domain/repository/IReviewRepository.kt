package com.anggarad.dev.foodfinder.core.domain.repository

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.ReviewResponse
import com.anggarad.dev.foodfinder.core.domain.model.ReviewDetails
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface IReviewRepository {

    fun getRestoReviews(restoId: Int): Flow<Resource<List<ReviewDetails>>>
    suspend fun postReview(
        restoId: Int,
        userId: Int,
        rating: Double,
        comments: String,
        fileName: String,
        body: RequestBody
    ): Flow<ApiResponse<ReviewResponse>>

    fun getUsersReview(userId: Int): Flow<Resource<List<ReviewDetails>>>
}