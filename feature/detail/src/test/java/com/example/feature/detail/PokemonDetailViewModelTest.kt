package com.example.feature.detail

import com.example.core.domain.usecase.GetPokemonDetailUseCase
import com.example.core.domain.usecase.ToggleFavoriteUseCase
import com.example.core.model.PokemonDetail
import com.example.core.testing.data.TestData
import com.example.core.testing.repository.FakeFavoriteRepository
import com.example.core.testing.repository.FakePokemonRepository
import com.example.core.testing.rule.MainDispatcherRule
import com.example.core.ui.state.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonDetailViewModelTest {

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
    fun `initial state is uninitialized`() {
        assertEquals(UiState.Uninitialized, viewModel.pokemonDetail.value)
    }

    @Test
    fun `loading the detail emits loading then success`() = runTest {
        pokemonRepository.detailResult = Result.success(TestData.bulbasaur)
        pokemonRepository.detailDelayMillis = 1_000

        viewModel.getPokemonDetail("bulbasaur")
        assertEquals(UiState.Loading, viewModel.pokemonDetail.value)

        advanceUntilIdle()
        assertEquals(UiState.Success(TestData.bulbasaur), viewModel.pokemonDetail.value)
    }

    @Test
    fun `failed load emits an error with the failure message`() = runTest {
        pokemonRepository.detailResult = Result.failure(IllegalStateException("Not found"))

        viewModel.getPokemonDetail("missingno")

        assertEquals(UiState.Error<PokemonDetail>(message = "Not found"), viewModel.pokemonDetail.value)
    }

    @Test
    fun `error is cleared once it has been shown`() = runTest {
        pokemonRepository.detailResult = Result.failure(IllegalStateException("Not found"))
        viewModel.getPokemonDetail("missingno")

        viewModel.onErrorShown()

        assertEquals(UiState.Uninitialized, viewModel.pokemonDetail.value)
    }

    @Test
    fun `clearing the error keeps a loaded detail untouched`() = runTest {
        pokemonRepository.detailResult = Result.success(TestData.bulbasaur)
        viewModel.getPokemonDetail("bulbasaur")

        viewModel.onErrorShown()

        assertEquals(UiState.Success(TestData.bulbasaur), viewModel.pokemonDetail.value)
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
}
