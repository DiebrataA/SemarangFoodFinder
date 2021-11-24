package com.anggarad.dev.foodfinder.core.domain.usecase

import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    fun checkToken() : Flow<String?>
}