package com.example.data.mapper

import com.example.core.model.EvolutionUIModel
import com.example.core.model.PokemonDetailResponse
import com.example.core.model.PokemonDetailUIModel
import com.example.core.model.PokemonListResponse
import com.example.core.model.PokemonListUiModel
import com.example.core.model.PokemonSpeciesResponse
import com.example.core.model.StatUIModel
import com.example.core.utils.PokemonSpeciesColor
import java.util.Locale

fun PokemonListResponse.toUiModel(): List<PokemonListUiModel> {
    return results?.map {
        val id = it.url?.trimEnd('/')?.split("/")?.last()?.toInt()
        PokemonListUiModel(
            name = it.name ?: "",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
        )
    } ?: emptyList()
}

fun PokemonSpeciesResponse.toFlavorText(): String? {
    val lang = Locale.getDefault().language
    return flavorTextEntries
        ?.firstOrNull { it?.language?.name.equals(lang, true) }
        ?.flavorText
        ?.replace("\n", " ")
        ?.replace("\u000c", " ")
        ?.trim()
}

fun PokemonDetailResponse.toUIModel(
    flavorText: String? = null,
    color: String? = null,
    evolutionList: List<EvolutionUIModel>? = null
): PokemonDetailUIModel {
    return PokemonDetailUIModel(
        id = id ?: 0,
        name = name?.replaceFirstChar { it.uppercase() }.orEmpty(),
        imageUrl = sprites?.other?.officialArtwork?.frontDefault.orEmpty(),
        types = types?.map {
            it.type.name.replaceFirstChar { c -> c.uppercase() }
        } ?: emptyList(),
        abilities = abilities?.map {
            it.ability.name.replaceFirstChar { c -> c.uppercase() }
        } ?: emptyList(),
        stats = stats?.map {
            StatUIModel(
                name = it.stat.name.replace("-", " ").replaceFirstChar { c -> c.uppercase() },
                value = it.base_stat
            )
        } ?: emptyList(),
        height = "${height?.div(10.0)} m",
        weight = "${weight?.div(10.0)} kg",
        moves = moves?.take(10)?.map {
            it.move.name.replaceFirstChar { c -> c.uppercase() }
        } ?: emptyList(),
        description = flavorText.orEmpty(),
        color = PokemonSpeciesColor.fromString(color.toString()).color,
        isFavorite = false,
        evolutionList = evolutionList ?: emptyList()
    )
}
