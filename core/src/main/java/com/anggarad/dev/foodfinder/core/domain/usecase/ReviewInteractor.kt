package com.anggarad.dev.foodfinder.core.domain.usecase

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.PostReviewResponse
import com.anggarad.dev.foodfinder.core.domain.model.ReviewDetails
import com.anggarad.dev.foodfinder.core.domain.repository.IReviewRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

class ReviewInteractor(private val reviewRepository: IReviewRepository) : ReviewUseCase {
    override fun getRestoReviews(restoId: Int): Flow<Resource<List<ReviewDetails>>> {
        return reviewRepository.getRestoReviews(restoId)
    }

    override suspend fun postReview(
        token: String,
        restoId: Int,
        userId: Int,
        rating: Float,
        comments: String,
        fileName: String,
        body: RequestBody?
    ): Flow<ApiResponse<PostReviewResponse>> {
        return reviewRepository.postReview(token, restoId, userId, rating, comments, fileName, body)
    }

    override fun getToken(): Flow<String> {
        return reviewRepository.getToken()
    }

//    override fun getUsersReview(userId: Int): Flow<Resource<List<ReviewDetails>>> {
//        return reviewRepository.getUsersReview(userId)
//    }
}