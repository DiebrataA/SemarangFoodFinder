package com.anggarad.dev.foodfinder.core.domain.usecase

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun getUserDetail() : Flow<Resource<UserDetail>>
    fun userLogin() : Flow<Resource<UserDetail>>
}