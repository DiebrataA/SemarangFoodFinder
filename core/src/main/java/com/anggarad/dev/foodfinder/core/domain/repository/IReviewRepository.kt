package com.anggarad.dev.foodfinder.core.domain.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.PostReviewResponse
import com.anggarad.dev.foodfinder.core.domain.model.ReviewDetails
import kotlinx.coroutines.flow.Flow

interface IReviewRepository {

    fun getRestoReviews(restoId: Int): Flow<Resource<List<ReviewDetails>>>
    suspend fun postReview(
        restoId: Int,
        userId: Int,
        rating: Float,
        comments: String,
        imgReviewPath: String
    ): Flow<ApiResponse<PostReviewResponse>>

    fun postImage(uri: Uri, uid: String, type: String, name: String): LiveData<Resource<String>>

    fun getToken(): Flow<String>

//    fun getUsersReview(userId: Int): Flow<Resource<List<ReviewDetails>>>
}