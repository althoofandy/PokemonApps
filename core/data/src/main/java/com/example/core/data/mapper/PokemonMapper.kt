package com.example.core.data.mapper

import com.example.core.data.network.model.PokemonChainResponse
import com.example.core.data.network.model.PokemonDetailResponse
import com.example.core.data.network.model.PokemonListResponse
import com.example.core.data.network.model.PokemonSpeciesResponse
import com.example.core.model.Pokemon
import com.example.core.model.PokemonDetail
import com.example.core.model.PokemonEvolution
import com.example.core.model.PokemonStat
import java.util.Locale

private const val OFFICIAL_ARTWORK_URL =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/%s.png"
private const val FALLBACK_FLAVOR_TEXT_LANGUAGE = "en"
private const val MAX_MOVES = 10

internal fun PokemonListResponse.toPokemonList(): List<Pokemon> =
    results.orEmpty().map {
        Pokemon(
            name = it.name.orEmpty(),
            imageUrl = officialArtworkUrl(it.url.orEmpty().lastPathSegment())
        )
    }

internal fun PokemonSpeciesResponse.toFlavorText(): String? {
    val lang = Locale.getDefault().language
    val entries = flavorTextEntries.orEmpty().filterNotNull()
    return (entries.firstOrNull { it.language?.name.equals(lang, true) }
        ?: entries.firstOrNull { it.language?.name == FALLBACK_FLAVOR_TEXT_LANGUAGE })
        ?.flavorText
        ?.replace("\n", " ")
        ?.replace("\u000c", " ")
        ?.trim()
}

internal fun PokemonDetailResponse.toPokemonDetail(
    species: PokemonSpeciesResponse,
    evolutionChain: PokemonChainResponse?
): PokemonDetail = PokemonDetail(
    id = id ?: 0,
    name = name?.replaceFirstChar { it.uppercase() }.orEmpty(),
    imageUrl = sprites?.other?.officialArtwork?.frontDefault.orEmpty(),
    types = types.orEmpty().map { it.type.name.replaceFirstChar { c -> c.uppercase() } },
    abilities = abilities.orEmpty().map { it.ability.name.replaceFirstChar { c -> c.uppercase() } },
    stats = stats.orEmpty().map {
        PokemonStat(
            name = it.stat.name.replace("-", " ").replaceFirstChar { c -> c.uppercase() },
            value = it.base_stat
        )
    },
    heightInMeters = (height ?: 0) / 10.0,
    weightInKg = (weight ?: 0) / 10.0,
    moves = moves.orEmpty().take(MAX_MOVES).map { it.move.name.replaceFirstChar { c -> c.uppercase() } },
    description = species.toFlavorText().orEmpty(),
    speciesColor = species.color?.name,
    evolutions = evolutionChain?.toEvolutions().orEmpty()
)

internal fun PokemonChainResponse.toEvolutions(): List<PokemonEvolution> {
    val result = mutableListOf<PokemonEvolution>()

    fun traverse(node: PokemonChainResponse.Chain) {
        result.add(
            PokemonEvolution(
                name = node.species.name,
                imageUrl = officialArtworkUrl(node.species.url.lastPathSegment()),
                minLevel = node.evolutionDetails?.firstOrNull()?.minLevel
            )
        )
        node.evolvesTo.forEach(::traverse)
    }

    traverse(chain)
    return result
}

private fun officialArtworkUrl(id: String): String = OFFICIAL_ARTWORK_URL.format(id)

private fun String.lastPathSegment(): String = trimEnd('/').substringAfterLast('/')
