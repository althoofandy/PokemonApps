package com.example.core.domain.usecase

import com.example.core.testing.data.TestData
import com.example.core.testing.repository.FakeFavoriteRepository
import com.example.core.testing.repository.FakePokemonRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetPokemonDetailUseCaseTest {

    private val pokemonRepository = FakePokemonRepository()
    private val favoriteRepository = FakeFavoriteRepository()
    private lateinit var useCase: GetPokemonDetailUseCase

    @Before
    fun setUp() {
        useCase = GetPokemonDetailUseCase(pokemonRepository, favoriteRepository)
    }

    @Test
    fun `returns detail marked as favorite when it is saved`() = runTest {
        pokemonRepository.detailResult = Result.success(TestData.bulbasaur)
        favoriteRepository.addFavorite(TestData.bulbasaurFavorite)

        val result = useCase("bulbasaur")

        assertTrue(result.getOrThrow().isFavorite)
    }

    @Test
    fun `returns detail not marked as favorite when it is not saved`() = runTest {
        pokemonRepository.detailResult = Result.success(TestData.bulbasaur)

        val result = useCase("bulbasaur")

        assertFalse(result.getOrThrow().isFavorite)
    }

    @Test
    fun `propagates repository failure`() = runTest {
        val error = IllegalStateException("Network error")
        pokemonRepository.detailResult = Result.failure(error)

        val result = useCase("bulbasaur")

        assertEquals(error, result.exceptionOrNull())
    }

    @Test
    fun `requests detail for the given name`() = runTest {
        pokemonRepository.detailResult = Result.success(TestData.bulbasaur)

        useCase("deoxys-normal")

        assertEquals(listOf("deoxys-normal"), pokemonRepository.requestedDetails)
    }
}
