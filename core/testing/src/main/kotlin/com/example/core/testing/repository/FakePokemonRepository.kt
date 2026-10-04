package com.example.core.testing.repository

import androidx.paging.PagingData
import com.example.core.domain.repository.PokemonRepository
import com.example.core.model.Pokemon
import com.example.core.model.PokemonDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakePokemonRepository : PokemonRepository {

    var pokemonList: List<Pokemon> = emptyList()
    var detailResult: Result<PokemonDetail> = Result.failure(IllegalStateException("No detail set"))
    var detailDelayMillis: Long = 0

    val requestedQueries = mutableListOf<String>()
    val requestedDetails = mutableListOf<String>()

    override fun getPokemonList(query: String): Flow<PagingData<Pokemon>> {
        requestedQueries += query
        val filtered = pokemonList.filter { it.name.contains(query, ignoreCase = true) }
        return flowOf(PagingData.from(filtered))
    }

    override suspend fun getPokemonDetail(name: String): Result<PokemonDetail> {
        requestedDetails += name
        if (detailDelayMillis > 0) delay(detailDelayMillis)
        return detailResult
    }
}
