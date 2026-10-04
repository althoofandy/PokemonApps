package com.example.core.domain.usecase

import com.example.core.testing.data.TestData
import com.example.core.testing.repository.FakeFavoriteRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ToggleFavoriteUseCaseTest {

    private val favoriteRepository = FakeFavoriteRepository()
    private val useCase = ToggleFavoriteUseCase(favoriteRepository)

    @Test
    fun `adds pokemon when it is not a favorite yet`() = runTest {
        val isFavorite = useCase(TestData.bulbasaur)

        assertTrue(isFavorite)
        assertEquals(listOf(TestData.bulbasaurFavorite), favoriteRepository.currentFavorites)
    }

    @Test
    fun `removes pokemon when it is already a favorite`() = runTest {
        favoriteRepository.addFavorite(TestData.bulbasaurFavorite)

        val isFavorite = useCase(TestData.bulbasaur)

        assertFalse(isFavorite)
        assertTrue(favoriteRepository.currentFavorites.isEmpty())
    }
}
