package com.example.core.data.mapper

import com.example.core.data.fake.ResponseFactory
import com.example.core.data.network.model.PokemonListResponse
import com.example.core.data.network.model.PokemonResult
import com.example.core.model.PokemonEvolution
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.util.Locale

class PokemonMapperTest {

    private lateinit var defaultLocale: Locale

    @Before
    fun setUp() {
        defaultLocale = Locale.getDefault()
        Locale.setDefault(Locale.ENGLISH)
    }

    @After
    fun tearDown() {
        Locale.setDefault(defaultLocale)
    }

    @Test
    fun `list response maps the id in the url to the official artwork`() {
        val response = PokemonListResponse(
            results = listOf(PokemonResult("pikachu", "https://pokeapi.co/api/v2/pokemon/25/"))
        )

        val pokemon = response.toPokemonList().single()

        assertEquals("pikachu", pokemon.name)
        assertEquals(
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png",
            pokemon.imageUrl
        )
    }

    @Test
    fun `flavor text uses the current language and cleans line breaks`() {
        Locale.setDefault(Locale.FRENCH)
        val species = ResponseFactory.species(
            flavorTexts = mapOf("en" to "English text", "fr" to "Texte\nfran\u000cçais")
        )

        assertEquals("Texte fran çais", species.toFlavorText())
    }

    @Test
    fun `flavor text falls back to English when the language is missing`() {
        Locale.setDefault(Locale.forLanguageTag("id"))
        val species = ResponseFactory.species(flavorTexts = mapOf("en" to "English text"))

        assertEquals("English text", species.toFlavorText())
    }

    @Test
    fun `flavor text is null when there is no matching or English entry`() {
        val species = ResponseFactory.species(flavorTexts = mapOf("ja" to "日本語"))

        assertNull(species.toFlavorText())
    }

    @Test
    fun `detail response maps units, names, moves and species data`() {
        val detail = ResponseFactory.detail(id = 1, name = "bulbasaur", height = 7, weight = 69)
            .toPokemonDetail(
                species = ResponseFactory.species(color = "green"),
                evolutionChain = null
            )

        assertEquals("Bulbasaur", detail.name)
        assertEquals(0.7, detail.heightInMeters, 0.0)
        assertEquals(6.9, detail.weightInKg, 0.0)
        assertEquals(listOf("Grass", "Poison"), detail.types)
        assertEquals("Special attack", detail.stats.single().name)
        assertEquals(10, detail.moves.size)
        assertEquals("A strange seed.", detail.description)
        assertEquals("green", detail.speciesColor)
        assertEquals(emptyList<PokemonEvolution>(), detail.evolutions)
    }

    @Test
    fun `evolution chain is flattened in order with the level of each stage`() {
        val chain = ResponseFactory.chain(
            Triple(1, "bulbasaur", null),
            Triple(2, "ivysaur", 16),
            Triple(3, "venusaur", 32)
        )

        val evolutions = chain.toEvolutions()

        assertEquals(listOf("bulbasaur", "ivysaur", "venusaur"), evolutions.map { it.name })
        assertEquals(listOf(null, 16, 32), evolutions.map { it.minLevel })
        assertEquals(
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png",
            evolutions.last().imageUrl
        )
    }
}
