package com.example.core.domain.usecase

import com.example.core.domain.repository.FavoriteRepository
import com.example.core.model.FavoritePokemon
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke(): Flow<List<FavoritePokemon>> = favoriteRepository.getFavorites()
}
