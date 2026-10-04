package com.example.core.domain.usecase

import com.example.core.testing.repository.FakePokemonRepository
import org.junit.Assert.assertEquals
import org.junit.Test

class GetPokemonListUseCaseTest {

    private val pokemonRepository = FakePokemonRepository()
    private val useCase = GetPokemonListUseCase(pokemonRepository)

    @Test
    fun `trims the search query before passing it to the repository`() {
        useCase("  mew  ")

        assertEquals(listOf("mew"), pokemonRepository.requestedQueries)
    }
}
