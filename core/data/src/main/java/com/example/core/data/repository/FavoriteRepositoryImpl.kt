package com.example.core.data.repository

import com.example.core.data.local.PokemonFavoriteDao
import com.example.core.data.mapper.toEntity
import com.example.core.data.mapper.toFavoritePokemon
import com.example.core.domain.repository.FavoriteRepository
import com.example.core.model.FavoritePokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class FavoriteRepositoryImpl(
    private val dao: PokemonFavoriteDao
) : FavoriteRepository {

    override fun getFavorites(): Flow<List<FavoritePokemon>> =
        dao.getAllFavorites().map { favorites -> favorites.map { it.toFavoritePokemon() } }

    override suspend fun isFavorite(id: Int): Boolean = dao.getById(id)?.isFavorite == true

    override suspend fun addFavorite(pokemon: FavoritePokemon) {
        dao.addFavorite(pokemon.toEntity())
    }

    override suspend fun removeFavorite(id: Int) {
        dao.removeFavorite(id)
    }
}
