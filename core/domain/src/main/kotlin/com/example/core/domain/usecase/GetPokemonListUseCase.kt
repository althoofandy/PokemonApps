package com.example.core.domain.usecase

import androidx.paging.PagingData
import com.example.core.domain.repository.PokemonRepository
import com.example.core.model.Pokemon
import kotlinx.coroutines.flow.Flow

class GetPokemonListUseCase(
    private val pokemonRepository: PokemonRepository
) {
    operator fun invoke(query: String): Flow<PagingData<Pokemon>> =
        pokemonRepository.getPokemonList(query.trim())
}
