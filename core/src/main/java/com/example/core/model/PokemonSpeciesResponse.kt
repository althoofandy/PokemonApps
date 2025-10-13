package com.example.core.model

import com.google.gson.annotations.SerializedName

data class PokemonSpeciesResponse(
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry?>? = null,
    val color: PokemonColor? = null,
    @SerializedName("evolution_chain")
    val evolutionChain: EvolutionChainRef? = null
)

data class PokemonColor(
    val name: String? = null
)

data class FlavorTextEntry(
    @SerializedName("flavor_text")
    val flavorText: String? = null,
    @SerializedName("language")
    val language: Language? = null
)

data class Language(
    @SerializedName("name")
    val name: String? = null
)

data class EvolutionChainRef(
    val url: String? = null
)
