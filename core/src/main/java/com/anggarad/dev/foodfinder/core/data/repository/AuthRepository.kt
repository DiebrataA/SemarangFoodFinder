package com.anggarad.dev.foodfinder.core.data.repository

import androidx.lifecycle.LiveData
import com.anggarad.dev.foodfinder.core.data.DataStoreManager
import com.anggarad.dev.foodfinder.core.data.NetworkOnlyResource
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.RemoteDataSource
import com.anggarad.dev.foodfinder.core.data.source.local.LocalDataSource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.LoginResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.RegisterResponse
import com.anggarad.dev.foodfinder.core.domain.model.CurrentUserModel
import com.anggarad.dev.foodfinder.core.domain.model.LoginModel
import com.anggarad.dev.foodfinder.core.domain.model.RegisterModel
import com.anggarad.dev.foodfinder.core.domain.model.UserRegister
import com.anggarad.dev.foodfinder.core.domain.repository.IAuthRepository
import com.anggarad.dev.foodfinder.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepository(
    private val dataStoreManager: DataStoreManager,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IAuthRepository {
    override suspend fun saveCredentials(userId: Int) {
        return dataStoreManager.saveToDataStore(userId)
    }

    override suspend fun saveUserInfo(userDetail: CurrentUserModel) {
        val userData = DataMapper.mapCurrentUserModelToEntity(userDetail)
        return localDataSource.insertUserData(userData)
    }

    override fun registerUserEmailFb(
        email: String,
        password: String,
        user: UserRegister
    ): LiveData<Resource<UserRegister>> {
        return remoteDataSource.registerWithEmailFb(email, password, user)
    }

    override fun loginWithEmailFb(
        email: String,
        password: String
    ): LiveData<Resource<UserRegister>> {
        return remoteDataSource.loginWithEmailFb(email, password)
    }


    override suspend fun userLogin(email: String, password: String): Flow<Resource<LoginModel>> =
        object : NetworkOnlyResource<LoginModel, LoginResponse>() {
            override fun collectResult(data: LoginResponse): Flow<LoginModel> {
                return flow { emit(DataMapper.mapLoginResponseToDomain(data)) }
            }

            override suspend fun createCall(): Flow<ApiResponse<LoginResponse>> {
                return remoteDataSource.userLogin(email, password)
            }

        }.asFlow()

    override suspend fun userRegister(
        email: String,
        password: String,
        name: String,
        phoneNum: String,
        address: String
    ): Flow<Resource<RegisterModel>> =
        object : NetworkOnlyResource<RegisterModel, RegisterResponse>() {
            override fun collectResult(data: RegisterResponse): Flow<RegisterModel> {
                return flow { emit(DataMapper.mapRegisterResponseToDomain(data)) }
            }

            override suspend fun createCall(): Flow<ApiResponse<RegisterResponse>> {
                return remoteDataSource.userRegister(email, password, name, phoneNum, address)
            }

        }.asFlow()

}