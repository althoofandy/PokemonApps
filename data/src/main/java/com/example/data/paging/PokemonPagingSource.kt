package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.model.CoroutinesDispatcherProvider
import com.example.core.model.PokemonListUiModel
import com.example.core.network.ApiService
import com.example.data.mapper.toUiModel
import kotlinx.coroutines.withContext

class PokemonPagingSource(
    private val apiService: ApiService,
    private val dispatchers: CoroutinesDispatcherProvider,
    private val query: String,
    private val getAllPokemon: suspend () -> List<PokemonListUiModel>
) : PagingSource<Int, PokemonListUiModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListUiModel> {
        val offset = params.key ?: 0
        val limit = params.loadSize

        return try {
            val data = withContext(dispatchers.io) {
                if (query.isBlank()) {
                    apiService.getPokemonList(limit, offset).toUiModel()
                } else {
                    getAllPokemon()
                        .filter { it.name.contains(query.trim(), ignoreCase = true) }
                        .drop(offset)
                        .take(limit)
                }
            }

            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = if (data.size < limit) null else offset + limit
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonListUiModel>): Int? = null
}
