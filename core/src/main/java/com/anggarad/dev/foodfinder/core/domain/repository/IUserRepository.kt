package com.anggarad.dev.foodfinder.core.domain.repository

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun getUserDetail() : Flow<Resource<UserDetail>>
    fun userLogin() : Flow<Resource<UserDetail>>
}