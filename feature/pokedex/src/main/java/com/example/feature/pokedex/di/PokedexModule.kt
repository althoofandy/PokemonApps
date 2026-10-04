package com.example.feature.pokedex.di

import com.example.feature.pokedex.PokemonListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pokedexModule = module {
    viewModel { PokemonListViewModel(get()) }
}
