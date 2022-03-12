package com.anggarad.dev.foodfinder.core.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.anggarad.dev.foodfinder.core.data.DataStoreManager
import com.anggarad.dev.foodfinder.core.data.NetworkBoundResource
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.RemoteDataSource
import com.anggarad.dev.foodfinder.core.data.source.local.LocalDataSource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.PostReviewResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.ReviewItem
import com.anggarad.dev.foodfinder.core.domain.model.ReviewDetails
import com.anggarad.dev.foodfinder.core.domain.repository.IReviewRepository
import com.anggarad.dev.foodfinder.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReviewRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dataStoreManager: DataStoreManager
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

    override suspend fun postReview(

        restoId: Int,
        userId: Int,
        rating: Float,
        comments: String,
        imgReviewPath: String
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
        name: String
    ): LiveData<Resource<String>> {
        return remoteDataSource.postImage(uri, uid, type, name)
    }


}