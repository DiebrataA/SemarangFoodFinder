package com.anggarad.dev.foodfinder.core.data.repository

import com.anggarad.dev.foodfinder.core.data.NetworkBoundResource
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.RemoteDataSource
import com.anggarad.dev.foodfinder.core.data.source.local.LocalDataSource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiService
import com.anggarad.dev.foodfinder.core.data.source.remote.response.CurrUserItem
import com.anggarad.dev.foodfinder.core.data.source.remote.response.LoginResponse
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import com.anggarad.dev.foodfinder.core.domain.repository.IUserRepository
import com.anggarad.dev.foodfinder.core.utils.AppExecutors
import com.anggarad.dev.foodfinder.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AuthRepository (
    private val api: ApiService,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
        ): IUserRepository{
    override suspend fun getUserDetail(userId:Int): Flow<Resource<UserDetail>> {
        return object : NetworkBoundResource<UserDetail, CurrUserItem>(){
            override fun loadFromDB(): Flow<UserDetail> {
                    return localDataSource.getUserData(userId).map {
                        DataMapper.mapUserEntityToUser(it)
                    }
            }

            override fun shouldFetch(data: UserDetail?): Boolean {
                return data == null
            }

            override suspend fun createCall(): Flow<ApiResponse<CurrUserItem>> {
                return remoteDataSource.getUserDetail(userId)
            }

            override suspend fun saveCallResult(data: CurrUserItem) {
                val user = DataMapper.mapUserResponseToEntity(data)
                localDataSource.insertUserData(user)
            }

        }.asFlow()
    }


    override suspend fun userLogin(email: String, password: String): ApiResponse<LoginResponse> {
        return try {
                ApiResponse.Success(api.userLogin(email, password))
            } catch (e: Exception) {
                ApiResponse.Error(e.toString())
            }


        }

}