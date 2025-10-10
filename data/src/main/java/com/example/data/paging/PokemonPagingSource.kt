package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.model.CoroutinesDispatcherProvider
import com.example.core.model.PokemonListUiModel
import com.example.core.network.ApiService
import com.example.core.utils.NetworkResultWrapper
import com.example.core.utils.safeApiCall
import com.example.data.mapper.toUiModel
import kotlinx.coroutines.withContext

class PokemonPagingSource(
    private val apiService: ApiService,
    private val dispatchers: CoroutinesDispatcherProvider,
    private val query: String? = null
) : PagingSource<Int, PokemonListUiModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListUiModel> {
        val offset = params.key ?: 0
        val limit = params.loadSize

        return when (val result = safeApiCall { apiService.getPokemonList(limit, offset) }) {
            is NetworkResultWrapper.Success -> {
                val data = withContext(dispatchers.io) {
                    val pokemonList = result.data.toUiModel()
                    if (!query.isNullOrBlank()) {
                        pokemonList.filter {
                            it.name.contains(query, ignoreCase = true)
                        }
                    } else {
                        pokemonList
                    }
                }

                LoadResult.Page(
                    data = data,
                    prevKey = if (offset == 0) null else offset - limit,
                    nextKey = if (data.isEmpty()) null else offset + limit
                )
            }

            is NetworkResultWrapper.Error -> {
                LoadResult.Error(Exception(result.message))
            }

            is NetworkResultWrapper.Exception -> {
                LoadResult.Error(result.throwable)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonListUiModel>): Int? {
        return state.anchorPosition
    }
}
