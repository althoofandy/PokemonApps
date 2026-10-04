package com.example.feature.favorite

import com.example.core.domain.usecase.GetFavoritesUseCase
import com.example.core.testing.data.TestData
import com.example.core.testing.repository.FakeFavoriteRepository
import com.example.core.testing.rule.MainDispatcherRule
import com.example.core.ui.state.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritePokemonViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val favoriteRepository = FakeFavoriteRepository()
    private lateinit var viewModel: FavoritePokemonViewModel

    @Before
    fun setUp() {
        viewModel = FavoritePokemonViewModel(GetFavoritesUseCase(favoriteRepository))
    }

    @Test
    fun `state is loading until someone collects it`() {
        assertEquals(UiState.Loading, viewModel.favorites.value)
    }

    @Test
    fun `no favorites is exposed as empty`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.favorites.collect {} }

        assertEquals(UiState.Empty, viewModel.favorites.value)
    }

    @Test
    fun `favorites follow the repository`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.favorites.collect {} }

        favoriteRepository.addFavorite(TestData.bulbasaurFavorite)
        assertEquals(UiState.Success(listOf(TestData.bulbasaurFavorite)), viewModel.favorites.value)

        favoriteRepository.removeFavorite(TestData.bulbasaurFavorite.id)
        assertEquals(UiState.Empty, viewModel.favorites.value)
    }
}
