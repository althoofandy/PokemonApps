package com.example.core.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val abilities: List<String>,
    val stats: List<PokemonStat>,
    val heightInMeters: Double,
    val weightInKg: Double,
    val moves: List<String>,
    val description: String,
    val speciesColor: String?,
    val evolutions: List<PokemonEvolution>,
    val isFavorite: Boolean = false
)

data class PokemonStat(
    val name: String,
    val value: Int
)

data class PokemonEvolution(
    val name: String,
    val imageUrl: String,
    val minLevel: Int?
)
