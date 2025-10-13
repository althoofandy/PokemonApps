package com.example.core.model

import com.google.gson.annotations.SerializedName

data class PokemonChainResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("chain") val chain: Chain
) {
    data class Chain(
        @SerializedName("species") val species: Species,
        @SerializedName("evolution_details") val evolutionDetails: List<EvolutionDetail>?,
        @SerializedName("evolves_to") val evolvesTo: List<Chain>
    )

    data class Species(
        @SerializedName("name") val name: String,
        @SerializedName("url") val url: String
    )

    data class EvolutionDetail(
        @SerializedName("min_level") val minLevel: Int?,
        @SerializedName("trigger") val trigger: Trigger?
    )

    data class Trigger(
        @SerializedName("name") val name: String
    )
}


