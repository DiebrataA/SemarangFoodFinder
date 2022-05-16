package com.anggarad.dev.foodfinder.core.domain.usecase

import android.net.Uri
import androidx.lifecycle.LiveData
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.PostReviewResponse
import com.anggarad.dev.foodfinder.core.domain.model.PostEditReviewModel
import com.anggarad.dev.foodfinder.core.domain.model.ReviewDetails
import com.anggarad.dev.foodfinder.core.domain.model.SingleReviewModel
import com.anggarad.dev.foodfinder.core.domain.model.UserReviewDetails
import com.anggarad.dev.foodfinder.core.domain.repository.IReviewRepository
import kotlinx.coroutines.flow.Flow

class ReviewInteractor(private val reviewRepository: IReviewRepository) : ReviewUseCase {
    override fun getRestoReviews(restoId: Int): Flow<Resource<List<ReviewDetails>>> {
        return reviewRepository.getRestoReviews(restoId)
    }

    override suspend fun postReview(
        restoId: Int,
        userId: Int,
        rating: Float,
        comments: String,
        imgReviewPath: String
    ): Flow<ApiResponse<PostReviewResponse>> {
        return reviewRepository.postReview(restoId, userId, rating, comments, imgReviewPath)
    }

    override fun postImage(
        uri: Uri,
        uid: String,
        type: String,
        name: String,
    ): LiveData<Resource<String>> {
        return reviewRepository.postImage(uri, uid, type, name)
    }

    override fun getToken(): Flow<String> {
        return reviewRepository.getToken()
    }

    override fun updateUserReview(
        reviewId: Int?,
        comments: String?,
        rating: Float?,
        isDeleted: Int?,
    ): Flow<Resource<PostEditReviewModel>> {
        return reviewRepository.updateUserReview(reviewId, comments, rating, isDeleted)
    }

    override fun getUsersReview(userId: Int): Flow<Resource<List<UserReviewDetails>>> {
        return reviewRepository.getUsersReview(userId)
    }

    override fun getReviewById(reviewId: Int): Flow<Resource<SingleReviewModel>> {
        return reviewRepository.getReviewById(reviewId)
    }

//    override fun getUsersReview(userId: Int): Flow<Resource<List<ReviewDetails>>> {
//        return reviewRepository.getUsersReview(userId)
//    }
}