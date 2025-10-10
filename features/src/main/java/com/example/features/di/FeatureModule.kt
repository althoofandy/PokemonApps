package com.example.features.di

import com.example.features.viewmodel.FavoritePokemonViewModel
import com.example.features.viewmodel.PokemonDetailViewModel
import com.example.features.viewmodel.PokemonListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {
    viewModel { PokemonListViewModel(get()) }
    viewModel { PokemonDetailViewModel(get(), get(), get()) }
    viewModel { FavoritePokemonViewModel(get()) }
}