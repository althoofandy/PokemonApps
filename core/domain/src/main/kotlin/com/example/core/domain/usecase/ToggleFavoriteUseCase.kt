package com.example.core.domain.usecase

import com.example.core.domain.repository.FavoriteRepository
import com.example.core.model.FavoritePokemon
import com.example.core.model.PokemonDetail

class ToggleFavoriteUseCase(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(pokemon: PokemonDetail): Boolean {
        if (favoriteRepository.isFavorite(pokemon.id)) {
            favoriteRepository.removeFavorite(pokemon.id)
            return false
        }
        favoriteRepository.addFavorite(
            FavoritePokemon(id = pokemon.id, name = pokemon.name, imageUrl = pokemon.imageUrl)
        )
        return true
    }
}
