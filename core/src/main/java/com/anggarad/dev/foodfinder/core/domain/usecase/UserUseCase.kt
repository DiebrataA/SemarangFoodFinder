package com.anggarad.dev.foodfinder.core.domain.usecase

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.LoginResponse
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    suspend fun getUserDetail(userId: Int) : Flow<Resource<UserDetail>>
    suspend fun userLogin(email:String, password:String) : ApiResponse<LoginResponse>
}