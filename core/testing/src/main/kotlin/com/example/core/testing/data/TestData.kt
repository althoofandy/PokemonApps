package com.example.core.testing.data

import com.example.core.model.FavoritePokemon
import com.example.core.model.Pokemon
import com.example.core.model.PokemonDetail
import com.example.core.model.PokemonEvolution
import com.example.core.model.PokemonStat

object TestData {

    val pokemonList = listOf(
        Pokemon(name = "bulbasaur", imageUrl = "https://example.com/1.png"),
        Pokemon(name = "ivysaur", imageUrl = "https://example.com/2.png"),
        Pokemon(name = "mew", imageUrl = "https://example.com/151.png"),
        Pokemon(name = "mewtwo", imageUrl = "https://example.com/150.png")
    )

    val bulbasaur = PokemonDetail(
        id = 1,
        name = "Bulbasaur",
        imageUrl = "https://example.com/1.png",
        types = listOf("Grass", "Poison"),
        abilities = listOf("Overgrow", "Chlorophyll"),
        stats = listOf(PokemonStat(name = "Hp", value = 45)),
        heightInMeters = 0.7,
        weightInKg = 6.9,
        moves = listOf("Razor wind"),
        description = "A strange seed was planted on its back at birth.",
        speciesColor = "green",
        evolutions = listOf(
            PokemonEvolution(name = "bulbasaur", imageUrl = "https://example.com/1.png", minLevel = null),
            PokemonEvolution(name = "ivysaur", imageUrl = "https://example.com/2.png", minLevel = 16)
        )
    )

    val bulbasaurFavorite = FavoritePokemon(
        id = bulbasaur.id,
        name = bulbasaur.name,
        imageUrl = bulbasaur.imageUrl
    )
}
