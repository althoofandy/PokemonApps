package com.example.feature.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.core.domain.usecase.GetFavoritesUseCase
import com.example.core.model.FavoritePokemon
import com.example.core.testing.data.TestData
import com.example.core.testing.repository.FakeFavoriteRepository
import com.example.core.testing.rule.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavoritePokemonViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val favoriteRepository = FakeFavoriteRepository()
    private lateinit var viewModel: FavoritePokemonViewModel

    @Before
    fun setUp() {
        viewModel = FavoritePokemonViewModel(GetFavoritesUseCase(favoriteRepository))
    }

    @Test
    fun `favorites follow the repository`() = runTest {
        val values = mutableListOf<List<FavoritePokemon>>()
        val observer = Observer<List<FavoritePokemon>> { values += it }
        viewModel.favorites.observeForever(observer)

        favoriteRepository.addFavorite(TestData.bulbasaurFavorite)
        favoriteRepository.removeFavorite(TestData.bulbasaurFavorite.id)

        assertEquals(listOf(emptyList(), listOf(TestData.bulbasaurFavorite), emptyList()), values)
        viewModel.favorites.removeObserver(observer)
    }
}
