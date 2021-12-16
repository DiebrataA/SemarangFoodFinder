package com.anggarad.dev.foodfinder.di

import com.anggarad.dev.foodfinder.MainViewModel
import com.anggarad.dev.foodfinder.auth.AuthViewModel
import com.anggarad.dev.foodfinder.core.domain.usecase.*
import com.anggarad.dev.foodfinder.detail.DetailViewModel
import com.anggarad.dev.foodfinder.detail.ReviewViewModel
import com.anggarad.dev.foodfinder.home.HomeViewModel
import com.anggarad.dev.foodfinder.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<UserUseCase> { UserInteractor(get())  }
    factory<RestoUseCase> { RestoInteractor(get())}
    factory<MainUseCase> { MainInteractor(get()) }
    factory<ReviewUseCase> { ReviewInteractor(get()) }
    factory<AuthUseCase> { AuthInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { ReviewViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
}