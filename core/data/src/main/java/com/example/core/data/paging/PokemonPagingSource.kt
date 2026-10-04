package com.example.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.mapper.toPokemonList
import com.example.core.data.network.ApiService
import com.example.core.data.util.CoroutinesDispatcherProvider
import com.example.core.model.Pokemon
import kotlinx.coroutines.withContext

internal class PokemonPagingSource(
    private val apiService: ApiService,
    private val dispatchers: CoroutinesDispatcherProvider,
    private val query: String,
    private val getAllPokemon: suspend () -> List<Pokemon>
) : PagingSource<Int, Pokemon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val offset = params.key ?: 0
        val limit = params.loadSize

        return try {
            val data = withContext(dispatchers.io) {
                if (query.isBlank()) {
                    apiService.getPokemonList(limit, offset).toPokemonList()
                } else {
                    getAllPokemon()
                        .filter { it.name.contains(query, ignoreCase = true) }
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

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? = null
}
