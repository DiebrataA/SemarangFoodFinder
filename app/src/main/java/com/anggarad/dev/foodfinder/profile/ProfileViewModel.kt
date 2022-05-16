package com.anggarad.dev.foodfinder.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.EditUserModel
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import com.anggarad.dev.foodfinder.core.domain.usecase.UserUseCase

class ProfileViewModel(private val userUseCase: UserUseCase) : ViewModel() {


    val userId = userUseCase.getUserId().asLiveData()

    fun getUserDetail(userId: Int): LiveData<Resource<UserDetail>> {
        return userUseCase.getUserDetail(userId).asLiveData()
    }

    fun fetchUserDetail(userId: String): LiveData<Resource<UserDetail>> {
        return userUseCase.fetchUser(userId)
    }

    fun editProfile(userId: String, userDetail: UserDetail): LiveData<Resource<UserDetail>> {
        return userUseCase.editProfile(userId, userDetail)
    }

    suspend fun editUserDataDb(
        userId: Int?,
        name: String?,
        address: String?,
        phoneNum: String?,
        imgProfile: String?,
    ): LiveData<Resource<EditUserModel>> {
        return userUseCase.updateUserDb(userId, name, address, phoneNum, imgProfile).asLiveData()
    }


    fun postImageProfile(
        uri: Uri,
        uid: String,
        type: String,
        name: String,
    ): LiveData<Resource<String>> {
        return userUseCase.postImage(uri, uid, type, name)
    }

}