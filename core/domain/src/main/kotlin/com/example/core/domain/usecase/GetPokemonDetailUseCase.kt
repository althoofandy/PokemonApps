package com.example.core.domain.usecase

import com.example.core.domain.repository.FavoriteRepository
import com.example.core.domain.repository.PokemonRepository
import com.example.core.model.PokemonDetail

class GetPokemonDetailUseCase(
    private val pokemonRepository: PokemonRepository,
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(name: String): Result<PokemonDetail> =
        pokemonRepository.getPokemonDetail(name).map { detail ->
            detail.copy(isFavorite = favoriteRepository.isFavorite(detail.id))
        }
}
