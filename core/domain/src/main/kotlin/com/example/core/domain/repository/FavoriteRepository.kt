package com.example.core.domain.repository

import com.example.core.model.FavoritePokemon
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavorites(): Flow<List<FavoritePokemon>>
    suspend fun isFavorite(id: Int): Boolean
    suspend fun addFavorite(pokemon: FavoritePokemon)
    suspend fun removeFavorite(id: Int)
}
