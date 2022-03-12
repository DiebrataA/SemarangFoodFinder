package com.anggarad.dev.foodfinder.core.data.repository

import androidx.lifecycle.LiveData
import com.anggarad.dev.foodfinder.core.data.DataStoreManager
import com.anggarad.dev.foodfinder.core.data.NetworkBoundResource
import com.anggarad.dev.foodfinder.core.data.NetworkOnlyResource
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.RemoteDataSource
import com.anggarad.dev.foodfinder.core.data.source.local.LocalDataSource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.ResponseItem
import com.anggarad.dev.foodfinder.core.data.source.remote.response.UserResponse
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import com.anggarad.dev.foodfinder.core.domain.model.UserReviewDetails
import com.anggarad.dev.foodfinder.core.domain.repository.IUserRepository
import com.anggarad.dev.foodfinder.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val dataStoreManager: DataStoreManager,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IUserRepository {


    override fun getUserDetail(userId: Int): Flow<Resource<UserDetail>> =
        object : NetworkBoundResource<UserDetail, UserResponse>() {
            override fun loadFromDB(): Flow<UserDetail> {
                return localDataSource.getUserData().map {
                    DataMapper.mapUserEntityToUserDetail(it)
                }
            }

            override fun shouldFetch(data: UserDetail?): Boolean {
                return true
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

    override fun getUsersReview(userId: Int): Flow<Resource<List<UserReviewDetails>>> =
        object : NetworkOnlyResource<List<UserReviewDetails>, List<ResponseItem>>() {
            override fun collectResult(data: List<ResponseItem>): Flow<List<UserReviewDetails>> {

                return flow { emit(DataMapper.mapUserReviewResponseToDomain(data)) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResponseItem>>> {
                return remoteDataSource.getUsersReviews(userId)
            }

        }.asFlow()

    override fun fetchUser(userId: String): LiveData<Resource<UserDetail>> {
        return remoteDataSource.getUserData(userId)
    }
}