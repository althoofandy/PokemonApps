package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.core.model.CoroutinesDispatcherProvider
import com.example.core.model.PokemonDetailUIModel
import com.example.core.model.PokemonListUiModel
import com.example.core.network.ApiService
import com.example.core.utils.NetworkResultWrapper
import com.example.core.utils.UiState
import com.example.core.utils.safeApiCall
import com.example.data.mapper.toFlavorText
import com.example.data.mapper.toUIModel
import com.example.data.paging.PokemonPagingSource


class PokemonRepositoryImpl(
    private val apiService: ApiService,
    private val dispatcher: CoroutinesDispatcherProvider
) : PokemonRepository {

    override suspend fun getPokemonDetail(name: String): UiState<PokemonDetailUIModel> {
        val detailResponse = safeApiCall { apiService.getPokemonDetail(name) }
        val speciesResponse = safeApiCall { apiService.getPokemonSpecies(name) }

        return when {
            detailResponse is NetworkResultWrapper.Success
                    && speciesResponse is NetworkResultWrapper.Success -> {
                val flavorText = speciesResponse.data.toFlavorText()
                val colorSpecies = speciesResponse.data.color?.name
                UiState.Success(detailResponse.data.toUIModel(flavorText, colorSpecies))
            }

            detailResponse is NetworkResultWrapper.Error -> UiState.Error(detailResponse.message)
            speciesResponse is NetworkResultWrapper.Error -> UiState.Error(speciesResponse.message)
            else -> UiState.Error("Unknown Error")
        }
    }

    override fun getPokemonList(query: String): Pager<Int, PokemonListUiModel> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PokemonPagingSource(apiService, dispatcher, query) }
        )
    }
}

