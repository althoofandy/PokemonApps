package com.example.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.mapper.toPokemonDetail
import com.example.core.data.mapper.toPokemonList
import com.example.core.data.network.ApiService
import com.example.core.data.paging.PokemonPagingSource
import com.example.core.data.util.CoroutinesDispatcherProvider
import com.example.core.data.util.runSuspendCatching
import com.example.core.domain.repository.PokemonRepository
import com.example.core.model.Pokemon
import com.example.core.model.PokemonDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

internal class PokemonRepositoryImpl(
    private val apiService: ApiService,
    private val dispatchers: CoroutinesDispatcherProvider
) : PokemonRepository {

    private val allPokemonMutex = Mutex()
    private var allPokemonCache: List<Pokemon>? = null

    override fun getPokemonList(query: String): Flow<PagingData<Pokemon>> =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                PokemonPagingSource(apiService, dispatchers, query, ::getAllPokemon)
            }
        ).flow

    override suspend fun getPokemonDetail(name: String): Result<PokemonDetail> =
        withContext(dispatchers.io) {
            runSuspendCatching {
                val detail = apiService.getPokemonDetail(name)
                val species = apiService.getPokemonSpecies(detail.species?.name ?: name)
                val evolutionChain = species.evolutionChain?.url?.let {
                    apiService.getPokemonEvolutionChain(it)
                }
                detail.toPokemonDetail(species, evolutionChain)
            }
        }

    private suspend fun getAllPokemon(): List<Pokemon> = allPokemonMutex.withLock {
        allPokemonCache ?: apiService.getPokemonList(limit = ALL_POKEMON_LIMIT, offset = 0)
            .toPokemonList()
            .also { allPokemonCache = it }
    }

    private companion object {
        const val PAGE_SIZE = 20
        const val ALL_POKEMON_LIMIT = 10_000
    }
}
