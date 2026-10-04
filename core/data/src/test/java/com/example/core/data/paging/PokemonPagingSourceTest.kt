package com.example.core.data.paging

import androidx.paging.PagingSource
import com.example.core.data.fake.FakeApiService
import com.example.core.data.mapper.toPokemonList
import com.example.core.data.util.CoroutinesDispatcherProvider
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class PokemonPagingSourceTest {

    private val names = listOf("bulbasaur", "ivysaur", "venusaur", "charmander", "mew", "mewtwo")
    private val apiService = FakeApiService(names)
    private val dispatchers = CoroutinesDispatcherProvider(testing = true)

    private fun pagingSource(query: String) = PokemonPagingSource(
        apiService = apiService,
        dispatchers = dispatchers,
        query = query,
        getAllPokemon = { apiService.getPokemonList(limit = 100, offset = 0).toPokemonList() }
    )

    private suspend fun PokemonPagingSource.load(key: Int?, loadSize: Int) =
        load(PagingSource.LoadParams.Refresh(key, loadSize, placeholdersEnabled = false))

    @Test
    fun `blank query loads a page from the api`() = runTest {
        val result = pagingSource(query = "").load(key = 2, loadSize = 2)

        result as PagingSource.LoadResult.Page
        assertEquals(listOf("venusaur", "charmander"), result.data.map { it.name })
        assertNull(result.prevKey)
        assertEquals(4, result.nextKey)
        assertEquals(listOf(2 to 2), apiService.listRequests)
    }

    @Test
    fun `last page has no next key`() = runTest {
        val result = pagingSource(query = "").load(key = 4, loadSize = 3)

        result as PagingSource.LoadResult.Page
        assertEquals(listOf("mew", "mewtwo"), result.data.map { it.name })
        assertNull(result.nextKey)
    }

    @Test
    fun `search finds matches outside the first page`() = runTest {
        val result = pagingSource(query = "mew").load(key = null, loadSize = 2)

        result as PagingSource.LoadResult.Page
        assertEquals(listOf("mew", "mewtwo"), result.data.map { it.name })
    }

    @Test
    fun `search pages through filtered results`() = runTest {
        val source = pagingSource(query = "saur")

        val first = source.load(key = null, loadSize = 2) as PagingSource.LoadResult.Page
        val second = source.load(key = first.nextKey, loadSize = 2) as PagingSource.LoadResult.Page

        assertEquals(listOf("bulbasaur", "ivysaur"), first.data.map { it.name })
        assertEquals(listOf("venusaur"), second.data.map { it.name })
        assertNull(second.nextKey)
    }

    @Test
    fun `api failure returns an error result`() = runTest {
        apiService.failure = IOException("No connection")

        val result = pagingSource(query = "").load(key = null, loadSize = 2)

        assertTrue(result is PagingSource.LoadResult.Error)
    }
}
