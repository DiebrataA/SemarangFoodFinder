package com.anggarad.dev.foodfinder.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface IMainRepository {
    fun checkToken() : Flow<String?>
}