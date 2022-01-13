package com.anggarad.dev.favorite

import com.anggarad.dev.foodfinder.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favModule = module {
    viewModel { FavoriteViewModel(get()) }
}