package com.anggarad.dev.foodfinder.di

import com.anggarad.dev.foodfinder.MainViewModel
import com.anggarad.dev.foodfinder.auth.AuthViewModel
import com.anggarad.dev.foodfinder.core.domain.usecase.*
import com.anggarad.dev.foodfinder.detail.DetailViewModel
import com.anggarad.dev.foodfinder.favorite.FavoriteViewModel
import com.anggarad.dev.foodfinder.home.HomeViewModel
import com.anggarad.dev.foodfinder.profile.ProfileViewModel
import com.anggarad.dev.foodfinder.restolist.RestoByCategoryViewModel
import com.anggarad.dev.foodfinder.reviews.ReviewViewModel
import com.anggarad.dev.foodfinder.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<UserUseCase> { UserInteractor(get()) }
    factory<RestoUseCase> { RestoInteractor(get()) }
    factory<MainUseCase> { MainInteractor(get()) }
    factory<ReviewUseCase> { ReviewInteractor(get()) }
    factory<AuthUseCase> { AuthInteractor(get()) }
    factory<CategoriesUseCase> { CategoriesInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { ReviewViewModel(get(), get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { RestoByCategoryViewModel(get()) }
}