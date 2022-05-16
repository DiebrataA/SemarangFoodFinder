package com.anggarad.dev.foodfinder.core.domain.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.EditUserModel
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    //    fun getUserDetail(userId: Int): Flow<Resource<UserDetail>>
    fun getUserDetail(userId: Int): Flow<Resource<UserDetail>>

    fun getUserId(): Flow<Int>


    fun fetchUser(userId: String): LiveData<Resource<UserDetail>>

    fun editProfile(userId: String, userDetail: UserDetail): LiveData<Resource<UserDetail>>

    fun postImage(uri: Uri, uid: String, type: String, name: String): LiveData<Resource<String>>

    suspend fun updateUserDb(
        userId: Int?,
        name: String?,
        address: String?,
        phoneNum: String?,
        imgProfile: String?,
    ): Flow<Resource<EditUserModel>>

}