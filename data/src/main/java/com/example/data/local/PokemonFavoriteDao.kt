package com.example.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.PokemonFavoriteEntity

@Dao
interface PokemonFavoriteDao {

    @Query("SELECT * FROM pokemon_favorites")
    fun getAllFavorites(): LiveData<List<PokemonFavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(pokemon: PokemonFavoriteEntity)

    @Query("DELETE FROM pokemon_favorites WHERE id = :id")
    suspend fun removeFavorite(id: Int): Int

    @Update
    suspend fun updateFavorite(pokemon: PokemonFavoriteEntity)

    @Query("SELECT * FROM pokemon_favorites WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): PokemonFavoriteEntity?
}
