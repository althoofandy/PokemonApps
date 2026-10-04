package com.example.core.domain.usecase

import com.example.core.testing.data.TestData
import com.example.core.testing.repository.FakeFavoriteRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetFavoritesUseCaseTest {

    private val favoriteRepository = FakeFavoriteRepository()
    private val useCase = GetFavoritesUseCase(favoriteRepository)

    @Test
    fun `emits saved favorites`() = runTest {
        favoriteRepository.addFavorite(TestData.bulbasaurFavorite)

        assertEquals(listOf(TestData.bulbasaurFavorite), useCase().first())
    }
}
