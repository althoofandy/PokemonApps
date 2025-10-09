package com.example.data.repository

import androidx.lifecycle.LiveData
import com.example.data.local.PokemonFavoriteEntity

interface PokemonLocalRepository {
    fun getFavorites(): LiveData<List<PokemonFavoriteEntity>>
    suspend fun addFavorite(pokemon: PokemonFavoriteEntity)
    suspend fun removeFavorite(id: Int)
    suspend fun updateFavorite(pokemon: PokemonFavoriteEntity)
    suspend fun isFavorite(id: Int): Boolean
}