package com.anggarad.dev.foodfinder.core.domain.usecase

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.CurrentUserModel
import com.anggarad.dev.foodfinder.core.domain.model.LoginModel
import com.anggarad.dev.foodfinder.core.domain.model.RegisterModel
import com.anggarad.dev.foodfinder.core.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow

class AuthInteractor(private val authRepos: IAuthRepository) : AuthUseCase {
    override suspend fun saveCredential(token: String, userId: Int) {
        return authRepos.saveCredentials(token, userId)
    }

    override suspend fun saveUserInfo(userDetail: CurrentUserModel) {
        return authRepos.saveUserInfo(userDetail)
    }

    override suspend fun userLogin(email: String, password: String): Flow<Resource<LoginModel>> {
        return authRepos.userLogin(email, password)
    }

    override suspend fun userRegister(
        email: String,
        password: String,
        name: String,
        phoneNum: String,
        address: String
    ): Flow<Resource<RegisterModel>> {
        return authRepos.userRegister(email, password, name, phoneNum, address)
    }
}