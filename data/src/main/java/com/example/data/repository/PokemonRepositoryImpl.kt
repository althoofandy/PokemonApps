package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.core.model.CoroutinesDispatcherProvider
import com.example.core.model.PokemonDetailUIModel
import com.example.core.model.PokemonListUiModel
import com.example.core.network.ApiService
import com.example.core.utils.UiState
import com.example.core.utils.processResponse
import com.example.core.utils.safeApiCall
import com.example.data.mapper.toUIModel
import com.example.data.paging.PokemonPagingSource


class PokemonRepositoryImpl(
    private val apiService: ApiService,
    private val dispatcher: CoroutinesDispatcherProvider
) : PokemonRepository {

    override suspend fun getPokemonDetail(name: String): UiState<PokemonDetailUIModel> {
        return processResponse(
            safeApiCall { apiService.getPokemonDetail(name) }
        ) { response ->
            response.toUIModel()
        }
    }

    override fun getPokemonList(query: String): Pager<Int, PokemonListUiModel> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PokemonPagingSource(apiService, dispatcher, query) }
        )
    }
}

