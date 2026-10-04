package com.example.core.domain.di

import com.example.core.domain.usecase.GetFavoritesUseCase
import com.example.core.domain.usecase.GetPokemonDetailUseCase
import com.example.core.domain.usecase.GetPokemonListUseCase
import com.example.core.domain.usecase.ToggleFavoriteUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetPokemonListUseCase(get()) }
    factory { GetPokemonDetailUseCase(get(), get()) }
    factory { GetFavoritesUseCase(get()) }
    factory { ToggleFavoriteUseCase(get()) }
}
