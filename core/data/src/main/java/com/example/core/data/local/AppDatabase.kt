package com.example.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PokemonFavoriteEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonFavoriteDao(): PokemonFavoriteDao
}
