package com.example.feature.detail.di

import com.example.feature.detail.PokemonDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {
    viewModel { PokemonDetailViewModel(get(), get()) }
}
