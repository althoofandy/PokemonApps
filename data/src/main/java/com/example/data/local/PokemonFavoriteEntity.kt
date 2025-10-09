package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_favorites")
data class PokemonFavoriteEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    val isFavorite: Boolean
)

