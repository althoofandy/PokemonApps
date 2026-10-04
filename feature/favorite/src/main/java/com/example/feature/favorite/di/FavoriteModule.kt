package com.example.feature.favorite.di

import com.example.feature.favorite.FavoritePokemonViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoritePokemonViewModel(get()) }
}
