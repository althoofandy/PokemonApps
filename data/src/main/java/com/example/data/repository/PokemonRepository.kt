package com.example.data.repository

import androidx.paging.Pager
import com.example.core.model.PokemonDetailUIModel
import com.example.core.model.PokemonListUiModel
import com.example.core.utils.UiState

interface PokemonRepository {
    fun getPokemonList(query: String): Pager<Int, PokemonListUiModel>
    suspend fun getPokemonDetail(name: String): UiState<PokemonDetailUIModel>
}
