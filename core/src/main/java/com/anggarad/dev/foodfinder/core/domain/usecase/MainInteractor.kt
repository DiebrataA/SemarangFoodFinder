package com.anggarad.dev.foodfinder.core.domain.usecase

import com.anggarad.dev.foodfinder.core.domain.repository.IMainRepository
import kotlinx.coroutines.flow.Flow

class MainInteractor(private val mainRepos: IMainRepository) : MainUseCase {
    override fun checkToken(): Flow<String?> {
        return mainRepos.checkToken()
    }
}