package com.example.core.testing.repository

import com.example.core.domain.repository.FavoriteRepository
import com.example.core.model.FavoritePokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeFavoriteRepository : FavoriteRepository {

    private val favorites = MutableStateFlow<List<FavoritePokemon>>(emptyList())

    val currentFavorites: List<FavoritePokemon> get() = favorites.value

    override fun getFavorites(): Flow<List<FavoritePokemon>> = favorites

    override suspend fun isFavorite(id: Int): Boolean = favorites.value.any { it.id == id }

    override suspend fun addFavorite(pokemon: FavoritePokemon) {
        favorites.update { current -> current.filterNot { it.id == pokemon.id } + pokemon }
    }

    override suspend fun removeFavorite(id: Int) {
        favorites.update { current -> current.filterNot { it.id == id } }
    }
}
