package com.example.feature.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.example.core.domain.usecase.GetPokemonDetailUseCase
import com.example.core.domain.usecase.ToggleFavoriteUseCase
import com.example.core.model.PokemonDetail
import com.example.core.testing.data.TestData
import com.example.core.testing.repository.FakeFavoriteRepository
import com.example.core.testing.repository.FakePokemonRepository
import com.example.core.testing.rule.MainDispatcherRule
import com.example.core.ui.state.UiState
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PokemonDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val pokemonRepository = FakePokemonRepository()
    private val favoriteRepository = FakeFavoriteRepository()
    private lateinit var viewModel: PokemonDetailViewModel

    @Before
    fun setUp() {
        viewModel = PokemonDetailViewModel(
            getPokemonDetailUseCase = GetPokemonDetailUseCase(pokemonRepository, favoriteRepository),
            toggleFavoriteUseCase = ToggleFavoriteUseCase(favoriteRepository)
        )
    }

    @Test
    fun `loading the detail emits loading then success`() = runTest {
        pokemonRepository.detailResult = Result.success(TestData.bulbasaur)
        val states = viewModel.pokemonDetail.recordValues()

        viewModel.getPokemonDetail("bulbasaur")

        assertEquals(listOf(UiState.Loading, UiState.Success(TestData.bulbasaur)), states)
    }

    @Test
    fun `failed load emits an error with the failure message`() = runTest {
        pokemonRepository.detailResult = Result.failure(IllegalStateException("Not found"))

        viewModel.getPokemonDetail("missingno")

        assertEquals(UiState.Error<PokemonDetail>(message = "Not found"), viewModel.pokemonDetail.value)
    }

    @Test
    fun `toggling favorite saves the pokemon and updates the state`() = runTest {
        pokemonRepository.detailResult = Result.success(TestData.bulbasaur)
        viewModel.getPokemonDetail("bulbasaur")

        viewModel.toggleFavorite()

        assertEquals(true, viewModel.currentDetail()?.isFavorite)
        assertEquals(listOf(TestData.bulbasaurFavorite), favoriteRepository.currentFavorites)
    }

    @Test
    fun `toggling favorite twice removes the pokemon again`() = runTest {
        pokemonRepository.detailResult = Result.success(TestData.bulbasaur)
        viewModel.getPokemonDetail("bulbasaur")

        viewModel.toggleFavorite()
        viewModel.toggleFavorite()

        assertEquals(false, viewModel.currentDetail()?.isFavorite)
        assertTrue(favoriteRepository.currentFavorites.isEmpty())
    }

    @Test
    fun `toggling favorite before the detail is loaded does nothing`() = runTest {
        viewModel.toggleFavorite()

        assertTrue(favoriteRepository.currentFavorites.isEmpty())
    }

    private fun PokemonDetailViewModel.currentDetail(): PokemonDetail? =
        (pokemonDetail.value as? UiState.Success)?.data

    private fun <T> LiveData<T>.recordValues(): List<T> {
        val values = mutableListOf<T>()
        observeForever { values += it }
        return values
    }
}
