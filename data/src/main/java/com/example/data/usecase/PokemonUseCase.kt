package com.example.data.usecase

import androidx.paging.Pager
import com.example.core.model.PokemonDetailUIModel
import com.example.core.model.PokemonListUiModel
import com.example.core.utils.UiState


fun interface GetPokemonDetailUseCase : suspend (String) -> UiState<PokemonDetailUIModel>

fun interface GetPokemonListUseCase : (String) -> Pager<Int, PokemonListUiModel>