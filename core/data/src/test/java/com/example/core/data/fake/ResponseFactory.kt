package com.example.core.data.fake

import com.example.core.data.network.model.Ability
import com.example.core.data.network.model.AbilitySlot
import com.example.core.data.network.model.EvolutionChainRef
import com.example.core.data.network.model.FlavorTextEntry
import com.example.core.data.network.model.Language
import com.example.core.data.network.model.Move
import com.example.core.data.network.model.MoveSlot
import com.example.core.data.network.model.OfficialArtwork
import com.example.core.data.network.model.OtherSprites
import com.example.core.data.network.model.PokemonChainResponse
import com.example.core.data.network.model.PokemonColor
import com.example.core.data.network.model.PokemonDetailResponse
import com.example.core.data.network.model.PokemonSpeciesResponse
import com.example.core.data.network.model.SpeciesRef
import com.example.core.data.network.model.Sprites
import com.example.core.data.network.model.Stat
import com.example.core.data.network.model.StatSlot
import com.example.core.data.network.model.Type
import com.example.core.data.network.model.TypeSlot

internal object ResponseFactory {

    fun detail(
        id: Int,
        name: String,
        speciesName: String = name,
        height: Int = 7,
        weight: Int = 69
    ) = PokemonDetailResponse(
        id = id,
        name = name,
        sprites = Sprites(
            frontDefault = null,
            other = OtherSprites(OfficialArtwork(frontDefault = "https://example.com/$id.png"))
        ),
        types = listOf(TypeSlot(Type("grass")), TypeSlot(Type("poison"))),
        abilities = listOf(AbilitySlot(Ability("overgrow"))),
        stats = listOf(StatSlot(base_stat = 65, stat = Stat("special-attack"))),
        height = height,
        weight = weight,
        moves = (1..12).map { MoveSlot(Move("move-$it")) },
        species = SpeciesRef(name = speciesName, url = "https://pokeapi.co/api/v2/pokemon-species/$id/")
    )

    fun species(
        flavorTexts: Map<String, String> = mapOf("en" to "A strange seed."),
        color: String? = "green",
        evolutionChainUrl: String? = null
    ) = PokemonSpeciesResponse(
        flavorTextEntries = flavorTexts.map { (language, text) ->
            FlavorTextEntry(flavorText = text, language = Language(language))
        },
        color = color?.let { PokemonColor(it) },
        evolutionChain = evolutionChainUrl?.let { EvolutionChainRef(it) }
    )

    fun chain(vararg stages: Triple<Int, String, Int?>): PokemonChainResponse {
        val node = stages.reversed().fold(emptyList<PokemonChainResponse.Chain>()) { next, (id, name, level) ->
            listOf(
                PokemonChainResponse.Chain(
                    species = PokemonChainResponse.Species(
                        name = name,
                        url = "https://pokeapi.co/api/v2/pokemon-species/$id/"
                    ),
                    evolutionDetails = level?.let {
                        listOf(PokemonChainResponse.EvolutionDetail(minLevel = it, trigger = null))
                    }.orEmpty(),
                    evolvesTo = next
                )
            )
        }.single()
        return PokemonChainResponse(id = 1, chain = node)
    }
}
