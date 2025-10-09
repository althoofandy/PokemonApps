package com.example.data.repository

import androidx.lifecycle.LiveData
import com.example.data.local.PokemonFavoriteDao
import com.example.data.local.PokemonFavoriteEntity

class PokemonLocalRepositoryImpl(
    private val dao: PokemonFavoriteDao
) : PokemonLocalRepository {

    override fun getFavorites(): LiveData<List<PokemonFavoriteEntity>> = dao.getAllFavorites()

    override suspend fun addFavorite(pokemon: PokemonFavoriteEntity) {
        dao.addFavorite(pokemon)
    }

    override suspend fun removeFavorite(id: Int) {
        dao.removeFavorite(id)
    }

    override suspend fun updateFavorite(pokemon: PokemonFavoriteEntity) {
        val existing = dao.getById(pokemon.id)
        if (existing != null) {
            val updated = existing.copy(isFavorite = !existing.isFavorite)
            dao.updateFavorite(updated)
        } else {
            dao.addFavorite(pokemon.copy(isFavorite = true))
        }
    }

    override suspend fun isFavorite(id: Int): Boolean {
        return dao.getById(id)?.isFavorite == true
    }

}
