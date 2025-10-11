package com.example.core.model

data class PokemonListUiModel(
    val name: String,
    val imageUrl: String
)

data class PokemonDetailUIModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val abilities: List<String>,
    val stats: List<StatUIModel>,
    val height: String,
    val weight: String,
    val moves: List<String>,
    val description: String,
    val color: String,
    val isFavorite: Boolean
)

data class StatUIModel(
    val name: String,
    val value: Int,
    val maxValue: Int = 200
)
