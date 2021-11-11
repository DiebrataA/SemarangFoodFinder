package com.anggarad.dev.foodfinder.core.domain.repository

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.LoginResponse
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    suspend fun getUserDetail(userId:Int) : Flow<Resource<UserDetail>>
    suspend fun userLogin(email: String, password: String) : ApiResponse<LoginResponse>
}