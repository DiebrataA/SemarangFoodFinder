package com.anggarad.dev.foodfinder.core.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.anggarad.dev.foodfinder.core.data.DataStoreManager
import com.anggarad.dev.foodfinder.core.data.NetworkBoundResource
import com.anggarad.dev.foodfinder.core.data.NetworkOnlyResource
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.RemoteDataSource
import com.anggarad.dev.foodfinder.core.data.source.local.LocalDataSource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.PostReviewResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.ResponseItem
import com.anggarad.dev.foodfinder.core.data.source.remote.response.ReviewItem
import com.anggarad.dev.foodfinder.core.data.source.remote.response.SingleReviewItem
import com.anggarad.dev.foodfinder.core.domain.model.PostEditReviewModel
import com.anggarad.dev.foodfinder.core.domain.model.ReviewDetails
import com.anggarad.dev.foodfinder.core.domain.model.SingleReviewModel
import com.anggarad.dev.foodfinder.core.domain.model.UserReviewDetails
import com.anggarad.dev.foodfinder.core.domain.repository.IReviewRepository
import com.anggarad.dev.foodfinder.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ReviewRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dataStoreManager: DataStoreManager,
) : IReviewRepository {
    override fun getRestoReviews(restoId: Int): Flow<Resource<List<ReviewDetails>>> =
        object : NetworkBoundResource<List<ReviewDetails>, List<ReviewItem>>() {
            override fun loadFromDB(): Flow<List<ReviewDetails>> {
                return localDataSource.getRestoReviews(restoId).map {
                    DataMapper.mapReviewEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<ReviewDetails>?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ReviewItem>>> {
                return remoteDataSource.getRestoReviews(restoId)
            }

            override suspend fun saveCallResult(data: List<ReviewItem>) {
                val reviewList = DataMapper.mapReviewResponseTOEntity(data)
                return localDataSource.insertReview(reviewList)
            }

        }.asFlow()


    override fun getToken(): Flow<String> {
        return dataStoreManager.getUserToken
    }

    override fun getUsersReview(userId: Int): Flow<Resource<List<UserReviewDetails>>> =
        object : NetworkOnlyResource<List<UserReviewDetails>, List<ResponseItem>>() {
            override fun collectResult(data: List<ResponseItem>): Flow<List<UserReviewDetails>> {

                return flow { emit(DataMapper.mapUserReviewResponseToDomain(data)) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResponseItem>>> {
                return remoteDataSource.getUsersReviews(userId)
            }

        }.asFlow()

    override fun updateUserReview(
        reviewId: Int?,
        comments: String?,
        rating: Float?,
        isDeleted: Int?,
    ): Flow<Resource<PostEditReviewModel>> =
        object : NetworkOnlyResource<PostEditReviewModel, PostReviewResponse>() {
            override fun collectResult(data: PostReviewResponse): Flow<PostEditReviewModel> {
                return flow { emit(DataMapper.mapPostEditReviewResponseToDomain(data)) }
            }

            override suspend fun createCall(): Flow<ApiResponse<PostReviewResponse>> {
                return remoteDataSource.updateUserReview(
                    reviewId, comments, rating, isDeleted
                )
            }

        }.asFlow()

    override fun getReviewById(reviewId: Int): Flow<Resource<SingleReviewModel>> =
        object : NetworkOnlyResource<SingleReviewModel, SingleReviewItem>() {
            override fun collectResult(data: SingleReviewItem): Flow<SingleReviewModel> {
                return flow { emit(DataMapper.mapSingleReview(data)) }
            }

            override suspend fun createCall(): Flow<ApiResponse<SingleReviewItem>> {
                return remoteDataSource.getUsersReviewsById(reviewId)
            }
        }.asFlow()


    override suspend fun postReview(
        restoId: Int,
        userId: Int,
        rating: Float,
        comments: String,
        imgReviewPath: String,
    ): Flow<ApiResponse<PostReviewResponse>> {
        return remoteDataSource.postReviews(
            restoId,
            userId,
            rating,
            comments,
            imgReviewPath
        )
    }

    override fun postImage(
        uri: Uri,
        uid: String,
        type: String,
        name: String,
    ): LiveData<Resource<String>> {
        return remoteDataSource.postImage(uri, uid, type, name)
    }

}