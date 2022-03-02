package com.anggarad.dev.foodfinder.core.domain.repository

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.CurrentUserModel
import com.anggarad.dev.foodfinder.core.domain.model.LoginModel
import com.anggarad.dev.foodfinder.core.domain.model.RegisterModel
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    suspend fun saveCredentials(token: String, userId: Int)
    suspend fun saveUserInfo(userDetail: CurrentUserModel)
    suspend fun userLogin(email: String, password: String): Flow<Resource<LoginModel>>
    suspend fun userRegister(
        email: String,
        password: String,
        name: String,
        phoneNum: String,
        address: String
    ): Flow<Resource<RegisterModel>>
}