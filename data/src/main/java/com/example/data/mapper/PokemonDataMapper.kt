package com.example.data.mapper

import com.example.core.model.PokemonDetailResponse
import com.example.core.model.PokemonDetailUIModel
import com.example.core.model.PokemonListResponse
import com.example.core.model.PokemonListUiModel
import com.example.core.model.StatUIModel

fun PokemonListResponse.toUiModel(): List<PokemonListUiModel> {
    return results?.map {
        val id = it.url?.trimEnd('/')?.split("/")?.last()?.toInt()
        PokemonListUiModel(
            name = it.name ?: "",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
        )
    } ?: emptyList()
}

fun PokemonDetailResponse.toUIModel(): PokemonDetailUIModel {
    return PokemonDetailUIModel(
        id = id,
        name = name.replaceFirstChar { it.uppercase() },
        imageUrl = sprites.other.officialArtwork.frontDefault.orEmpty(),
        types = types.map { it.type.name.replaceFirstChar { c -> c.uppercase() } },
        abilities = abilities.map { it.ability.name.replaceFirstChar { c -> c.uppercase() } },
        stats = stats.map {
            StatUIModel(
                name = it.stat.name.replace("-", " ").replaceFirstChar { c -> c.uppercase() },
                value = it.base_stat
            )
        },
        height = "${height / 10.0} m",
        weight = "${weight / 10.0} kg",
        moves = moves.take(10).map { it.move.name.replaceFirstChar { c -> c.uppercase() } },
        isFavorite = false
    )
}