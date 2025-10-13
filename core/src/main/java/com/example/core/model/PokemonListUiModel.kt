package com.example.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class PokemonListUiModel(
    val name: String,
    val imageUrl: String
)

@Parcelize
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
    val isFavorite: Boolean,
    val evolutionList: List<EvolutionUIModel>
) : Parcelable

@Parcelize
data class StatUIModel(
    val name: String,
    val value: Int,
    val maxValue: Int = 200
) : Parcelable

@Parcelize
data class EvolutionUIModel(
    val name: String,
    val imageUrl: String,
    val level: String? = null
) : Parcelable
