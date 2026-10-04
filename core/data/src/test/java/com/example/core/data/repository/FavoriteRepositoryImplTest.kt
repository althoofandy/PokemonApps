package com.example.core.data.repository

import com.example.core.data.local.PokemonFavoriteDao
import com.example.core.data.local.PokemonFavoriteEntity
import com.example.core.model.FavoritePokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FavoriteRepositoryImplTest {

    private val dao = FakePokemonFavoriteDao()
    private val repository = FavoriteRepositoryImpl(dao)
    private val pikachu = FavoritePokemon(id = 25, name = "Pikachu", imageUrl = "https://example.com/25.png")

    @Test
    fun `added favorite is stored and emitted as a domain model`() = runTest {
        repository.addFavorite(pikachu)

        assertTrue(repository.isFavorite(25))
        assertEquals(listOf(pikachu), repository.getFavorites().first())
        assertTrue(dao.entities.value.single().isFavorite)
    }

    @Test
    fun `removed favorite is no longer a favorite`() = runTest {
        repository.addFavorite(pikachu)

        repository.removeFavorite(25)

        assertFalse(repository.isFavorite(25))
        assertTrue(repository.getFavorites().first().isEmpty())
    }

    private class FakePokemonFavoriteDao : PokemonFavoriteDao {
        val entities = MutableStateFlow<List<PokemonFavoriteEntity>>(emptyList())

        override fun getAllFavorites(): Flow<List<PokemonFavoriteEntity>> = entities

        override suspend fun addFavorite(pokemon: PokemonFavoriteEntity) {
            entities.update { current -> current.filterNot { it.id == pokemon.id } + pokemon }
        }

        override suspend fun removeFavorite(id: Int): Int {
            val before = entities.value.size
            entities.update { current -> current.filterNot { it.id == id } }
            return before - entities.value.size
        }

        override suspend fun getById(id: Int): PokemonFavoriteEntity? =
            entities.value.firstOrNull { it.id == id }
    }
}
