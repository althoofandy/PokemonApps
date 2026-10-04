package com.example.core.domain.repository

import androidx.paging.PagingData
import com.example.core.model.Pokemon
import com.example.core.model.PokemonDetail
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonList(query: String): Flow<PagingData<Pokemon>>
    suspend fun getPokemonDetail(name: String): Result<PokemonDetail>
}
