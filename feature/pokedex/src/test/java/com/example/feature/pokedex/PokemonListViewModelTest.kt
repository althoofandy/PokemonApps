package com.example.feature.pokedex

import androidx.paging.testing.asSnapshot
import com.example.core.domain.usecase.GetPokemonListUseCase
import com.example.core.testing.data.TestData
import com.example.core.testing.repository.FakePokemonRepository
import com.example.core.testing.rule.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(StandardTestDispatcher())

    private val pokemonRepository = FakePokemonRepository().apply {
        pokemonList = TestData.pokemonList
    }
    private lateinit var viewModel: PokemonListViewModel

    @Before
    fun setUp() {
        viewModel = PokemonListViewModel(GetPokemonListUseCase(pokemonRepository))
    }

    @Test
    fun `search is debounced so only the latest query is requested`() =
        runTest(mainDispatcherRule.testDispatcher) {
            backgroundScope.launch { viewModel.pokemonPagingData.collect {} }

            viewModel.searchPokemon("m")
            advanceTimeBy(100)
            viewModel.searchPokemon("me")
            advanceTimeBy(100)
            viewModel.searchPokemon("mew ")
            advanceTimeBy(300)
            runCurrent()

            assertEquals(listOf("mew"), pokemonRepository.requestedQueries)
        }

    @Test
    fun `paging data contains the search results`() =
        runTest(mainDispatcherRule.testDispatcher) {
            viewModel.searchPokemon("mew")

            val items = flowOf(viewModel.pokemonPagingData.first()).asSnapshot()

            assertEquals(listOf("mew", "mewtwo"), items.map { it.name })
        }

    @Test
    fun `blank query shows every pokemon`() = runTest(mainDispatcherRule.testDispatcher) {
        val items = flowOf(viewModel.pokemonPagingData.first()).asSnapshot()

        assertEquals(TestData.pokemonList, items)
    }
}
