package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.core.model.CoroutinesDispatcherProvider
import com.example.core.model.PokemonChainResponse
import com.example.core.model.PokemonDetailResponse
import com.example.core.model.PokemonListUiModel
import com.example.core.model.PokemonSpeciesResponse
import com.example.core.network.ApiService
import com.example.core.utils.NetworkResultWrapper
import com.example.core.utils.safeApiCall
import com.example.data.paging.PokemonPagingSource


class PokemonRepositoryImpl(
    private val apiService: ApiService,
    private val dispatcher: CoroutinesDispatcherProvider
) : PokemonRepository {

    override suspend fun getPokemonDetail(name: String): NetworkResultWrapper<PokemonDetailResponse> =
        safeApiCall { apiService.getPokemonDetail(name) }

    override suspend fun getPokemonSpecies(name: String): NetworkResultWrapper<PokemonSpeciesResponse> =
        safeApiCall { apiService.getPokemonSpecies(name) }

    override suspend fun getPokemonEvolutionChain(url: String): NetworkResultWrapper<PokemonChainResponse> =
        safeApiCall { apiService.getPokemonEvolutionChain(url) }

    override fun getPokemonList(query: String): Pager<Int, PokemonListUiModel> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PokemonPagingSource(apiService, dispatcher, query) }
        )
    }

    override fun getPokemonImageUrl(id: Int): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    }
}

