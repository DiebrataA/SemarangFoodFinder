package com.anggarad.dev.foodfinder.core.data.repository

import com.anggarad.dev.foodfinder.core.data.NetworkBoundResource
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.RemoteDataSource
import com.anggarad.dev.foodfinder.core.data.source.local.LocalDataSource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.ReviewItem
import com.anggarad.dev.foodfinder.core.data.source.remote.response.ReviewResponse
import com.anggarad.dev.foodfinder.core.domain.model.ReviewDetails
import com.anggarad.dev.foodfinder.core.domain.repository.IReviewRepository
import com.anggarad.dev.foodfinder.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.RequestBody

class ReviewRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IReviewRepository {
    override fun getRestoReviews(restoId: Int): Flow<Resource<List<ReviewDetails>>> =
        object : NetworkBoundResource<List<ReviewDetails>, List<ReviewItem>>() {
            override fun loadFromDB(): Flow<List<ReviewDetails>> {
                return localDataSource.getRestoReviews(restoId).map {
                    DataMapper.mapReviewEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<ReviewDetails>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ReviewItem>>> {
                return remoteDataSource.getRestoReviews(restoId)
            }

            override suspend fun saveCallResult(data: List<ReviewItem>) {
                val reviewList = DataMapper.mapReviewResponseTOEntity(data)
                return localDataSource.insertReview(reviewList)
            }

        }.asFlow()

    override suspend fun postReview(
        restoId: Int,
        userId: Int,
        rating: Double,
        comments: String,
        fileName: String,
        body: RequestBody
    ): Flow<ApiResponse<ReviewResponse>> {
        return remoteDataSource.postReviews(restoId, userId, rating, comments, fileName, body)
    }


    override fun getUsersReview(userId: Int): Flow<Resource<List<ReviewDetails>>> =
        object : NetworkBoundResource<List<ReviewDetails>, List<ReviewItem>>() {
            override fun loadFromDB(): Flow<List<ReviewDetails>> {
                return localDataSource.getUserReviews(userId).map {
                    DataMapper.mapReviewEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<ReviewDetails>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ReviewItem>>> {
                return remoteDataSource.getUsersReviews(userId)
            }

            override suspend fun saveCallResult(data: List<ReviewItem>) {
                val reviewList = DataMapper.mapReviewResponseTOEntity(data)
                return localDataSource.insertReview(reviewList)
            }

        }.asFlow()
}