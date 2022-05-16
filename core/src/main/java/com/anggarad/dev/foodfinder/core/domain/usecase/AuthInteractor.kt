package com.anggarad.dev.foodfinder.core.domain.usecase

import androidx.lifecycle.LiveData
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.CurrentUserModel
import com.anggarad.dev.foodfinder.core.domain.model.LoginModel
import com.anggarad.dev.foodfinder.core.domain.model.RegisterModel
import com.anggarad.dev.foodfinder.core.domain.model.UserRegister
import com.anggarad.dev.foodfinder.core.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow

class AuthInteractor(private val authRepos: IAuthRepository) : AuthUseCase {
    override suspend fun saveCredential(userId: Int) {
        return authRepos.saveCredentials(userId)
    }

    override suspend fun saveUserInfo(userDetail: CurrentUserModel) {
        return authRepos.saveUserInfo(userDetail)
    }

    override fun continueWithGoogle(idToken: String): LiveData<Resource<UserRegister>> {
        return authRepos.continueWithGoogle(idToken)
    }

    override fun loginWithEmailFb(
        email: String,
        password: String,
    ): LiveData<Resource<UserRegister>> {
        return authRepos.loginWithEmailFb(email, password)
    }

    override fun registerUserEmailFb(
        email: String,
        password: String,
        user: UserRegister,
    ): LiveData<Resource<UserRegister>> {
        return authRepos.registerUserEmailFb(email, password, user)
    }

    override suspend fun userLogin(email: String, password: String): Flow<Resource<LoginModel>> {
        return authRepos.userLogin(email, password)
    }

    override suspend fun userRegister(
        email: String?,
        password: String?,
        name: String?,
        phoneNum: String?,
        address: String?,
        imgProfile: String?,
    ): Flow<Resource<RegisterModel>> {
        return authRepos.userRegister(email, password, name, phoneNum, address, imgProfile)
    }
}