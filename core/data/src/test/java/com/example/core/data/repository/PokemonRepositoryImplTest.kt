package com.example.core.data.repository

import androidx.paging.testing.asSnapshot
import com.example.core.data.fake.FakeApiService
import com.example.core.data.fake.ResponseFactory
import com.example.core.data.util.CoroutinesDispatcherProvider
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class PokemonRepositoryImplTest {

    private val names = (1..30).map { "pokemon-$it" } + listOf("mew", "mewtwo")
    private val apiService = FakeApiService(names)
    private val repository = PokemonRepositoryImpl(
        apiService = apiService,
        dispatchers = CoroutinesDispatcherProvider(testing = true)
    )

    @Test
    fun `detail requests the species by species name for alternate forms`() = runTest {
        apiService.details["deoxys-normal"] =
            ResponseFactory.detail(id = 386, name = "deoxys-normal", speciesName = "deoxys")
        apiService.species["deoxys"] = ResponseFactory.species()

        val result = repository.getPokemonDetail("deoxys-normal")

        assertTrue(result.isSuccess)
        assertEquals(listOf("deoxys"), apiService.speciesRequests)
    }

    @Test
    fun `detail includes the evolution chain when the species has one`() = runTest {
        val chainUrl = "https://pokeapi.co/api/v2/evolution-chain/1/"
        apiService.details["bulbasaur"] = ResponseFactory.detail(id = 1, name = "bulbasaur")
        apiService.species["bulbasaur"] = ResponseFactory.species(evolutionChainUrl = chainUrl)
        apiService.evolutionChains[chainUrl] = ResponseFactory.chain(
            Triple(1, "bulbasaur", null),
            Triple(2, "ivysaur", 16)
        )

        val detail = repository.getPokemonDetail("bulbasaur").getOrThrow()

        assertEquals(listOf("bulbasaur", "ivysaur"), detail.evolutions.map { it.name })
    }

    @Test
    fun `detail skips the evolution request when the species has no chain`() = runTest {
        apiService.details["bulbasaur"] = ResponseFactory.detail(id = 1, name = "bulbasaur")
        apiService.species["bulbasaur"] = ResponseFactory.species(evolutionChainUrl = null)

        val detail = repository.getPokemonDetail("bulbasaur").getOrThrow()

        assertTrue(detail.evolutions.isEmpty())
        assertTrue(apiService.evolutionChainRequests.isEmpty())
    }

    @Test
    fun `detail returns a failure instead of throwing`() = runTest {
        val error = IOException("No connection")
        apiService.failure = error

        val result = repository.getPokemonDetail("bulbasaur")

        assertEquals(error, result.exceptionOrNull())
    }

    @Test
    fun `search returns matches from the whole list`() = runTest {
        val items = repository.getPokemonList("mew").asSnapshot()

        assertEquals(listOf("mew", "mewtwo"), items.map { it.name })
    }

    @Test
    fun `full pokemon list is fetched once and reused across searches`() = runTest {
        repository.getPokemonList("mew").asSnapshot()
        repository.getPokemonList("pokemon-1").asSnapshot()

        assertEquals(1, apiService.listRequests.count { (limit, _) -> limit > names.size })
    }
}
