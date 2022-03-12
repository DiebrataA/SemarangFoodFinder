package com.anggarad.dev.foodfinder.core.domain.repository

import androidx.lifecycle.LiveData
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.CurrentUserModel
import com.anggarad.dev.foodfinder.core.domain.model.LoginModel
import com.anggarad.dev.foodfinder.core.domain.model.RegisterModel
import com.anggarad.dev.foodfinder.core.domain.model.UserRegister
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    suspend fun saveCredentials(userId: Int)
    suspend fun saveUserInfo(userDetail: CurrentUserModel)
    fun loginWithEmailFb(email: String, password: String): LiveData<Resource<UserRegister>>
    fun registerUserEmailFb(
        email: String,
        password: String,
        user: UserRegister
    ): LiveData<Resource<UserRegister>>

    suspend fun userLogin(email: String, password: String): Flow<Resource<LoginModel>>
    suspend fun userRegister(
        email: String,
        password: String,
        name: String,
        phoneNum: String,
        address: String
    ): Flow<Resource<RegisterModel>>
}