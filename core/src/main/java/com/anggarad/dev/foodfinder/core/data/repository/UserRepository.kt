package com.anggarad.dev.foodfinder.core.data.repository

import com.anggarad.dev.foodfinder.core.data.DataStoreManager
import com.anggarad.dev.foodfinder.core.data.NetworkBoundResource
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.RemoteDataSource
import com.anggarad.dev.foodfinder.core.data.source.local.LocalDataSource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.ReviewItem
import com.anggarad.dev.foodfinder.core.data.source.remote.response.UserResponse
import com.anggarad.dev.foodfinder.core.domain.model.ReviewDetails
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import com.anggarad.dev.foodfinder.core.domain.repository.IUserRepository
import com.anggarad.dev.foodfinder.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val dataStoreManager: DataStoreManager,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IUserRepository {


    override fun getUserDetail(userId: Int): Flow<Resource<UserDetail>> =
        object : NetworkBoundResource<UserDetail, UserResponse>() {
            override fun loadFromDB(): Flow<UserDetail> {
                return localDataSource.getUserData(userId).map {
                    DataMapper.mapUserEntityToUserDetail(it)
                }
            }

            override fun shouldFetch(data: UserDetail?): Boolean {
                return data == null
            }

            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> {
                return remoteDataSource.getUserDetail(userId)
            }

            override suspend fun saveCallResult(data: UserResponse) {
                val userDetail = DataMapper.mapUserResponseToEntity(data)
                return localDataSource.insertUserData(userDetail)
            }

        }.asFlow()

    override fun getUserId(): Flow<Int> {
        return dataStoreManager.getUserId
    }

    override fun getUsersReview(userId: Int): Flow<Resource<List<ReviewDetails>>> =
        object : NetworkBoundResource<List<ReviewDetails>, List<ReviewItem>>() {
            override fun loadFromDB(): Flow<List<ReviewDetails>> {
                return localDataSource.getUserReviews(userId).map {
                    DataMapper.mapReviewEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<ReviewDetails>?): Boolean {
                return true
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