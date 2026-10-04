package com.example.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface PokemonFavoriteDao {

    @Query("SELECT * FROM pokemon_favorites")
    fun getAllFavorites(): Flow<List<PokemonFavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(pokemon: PokemonFavoriteEntity)

    @Query("DELETE FROM pokemon_favorites WHERE id = :id")
    suspend fun removeFavorite(id: Int): Int

    @Query("SELECT * FROM pokemon_favorites WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): PokemonFavoriteEntity?
}
