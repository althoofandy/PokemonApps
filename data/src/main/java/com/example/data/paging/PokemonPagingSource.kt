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
    private val query: String
) : PagingSource<Int, PokemonListUiModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListUiModel> {
        val offset = params.key ?: 0
        val limit = params.loadSize

        return try {
            val data = withContext(dispatchers.io) {
                apiService.getPokemonList(limit, offset).toUiModel()
            }
            val filtered = if (query.isNotBlank()) {
                data.filter { it.name.contains(query, ignoreCase = true) }
            } else {
                data
            }

            LoadResult.Page(
                data = filtered,
                prevKey = if (offset == 0) null else offset - limit,
                nextKey = if (filtered.isEmpty()) null else offset + limit
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonListUiModel>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(20) ?: anchorPage?.nextKey?.minus(20)
        }
    }
}


