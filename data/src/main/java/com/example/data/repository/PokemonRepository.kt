package com.example.data.repository

import androidx.paging.Pager
import com.example.core.model.PokemonChainResponse
import com.example.core.model.PokemonDetailResponse
import com.example.core.model.PokemonListUiModel
import com.example.core.model.PokemonSpeciesResponse
import com.example.core.utils.NetworkResultWrapper

interface PokemonRepository {
    fun getPokemonList(query: String): Pager<Int, PokemonListUiModel>
    suspend fun getPokemonDetail(name: String): NetworkResultWrapper<PokemonDetailResponse>
    suspend fun getPokemonSpecies(name: String): NetworkResultWrapper<PokemonSpeciesResponse>
    suspend fun getPokemonEvolutionChain(url: String): NetworkResultWrapper<PokemonChainResponse>
    fun getPokemonImageUrl(id: Int): String
}
