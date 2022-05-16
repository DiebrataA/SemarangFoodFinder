package com.anggarad.dev.foodfinder.core.domain.usecase

import android.net.Uri
import androidx.lifecycle.LiveData
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.EditUserModel
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import com.anggarad.dev.foodfinder.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserInteractor(private val userRepository: IUserRepository) : UserUseCase {


//    override fun getUserDetail(userId: Int): Flow<Resource<UserDetail>> {
//        return userRepository.getUserDetail(userId)
//    }

    override fun getUserDetail(userId: Int): Flow<Resource<UserDetail>> {
        return userRepository.getUserDetail(userId)
    }

    override fun getUserId(): Flow<Int> {
        return userRepository.getUserId()
    }

    override fun fetchUser(userId: String): LiveData<Resource<UserDetail>> {
        return userRepository.fetchUser(userId)
    }

    override fun editProfile(
        userId: String,
        userDetail: UserDetail,
    ): LiveData<Resource<UserDetail>> {
        return userRepository.editProfile(userId, userDetail)
    }

    override fun postImage(
        uri: Uri,
        uid: String,
        type: String,
        name: String,
    ): LiveData<Resource<String>> {
        return userRepository.postImage(uri, uid, type, name)
    }

    override suspend fun updateUserDb(
        userId: Int?,
        name: String?,
        address: String?,
        phoneNum: String?,
        imgProfile: String?,
    ): Flow<Resource<EditUserModel>> {
        return userRepository.updateUserDb(userId, name, address, phoneNum, imgProfile)
    }
}