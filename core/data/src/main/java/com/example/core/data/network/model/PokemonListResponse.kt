package com.example.core.data.network.model

import com.google.gson.annotations.SerializedName

internal data class PokemonListResponse(
    val results: List<PokemonResult>? = null
)

internal data class PokemonResult(
    val name: String? = null,
    val url: String? = null
)

internal data class PokemonDetailResponse(
    val id: Int? = null,
    val name: String? = null,
    val sprites: Sprites? = null,
    val types: List<TypeSlot>? = null,
    val abilities: List<AbilitySlot>? = null,
    val stats: List<StatSlot>? = null,
    val height: Int? = null,
    val weight: Int? = null,
    val description: String? = null,
    val moves: List<MoveSlot>? = null,
    val species: SpeciesRef? = null
)

internal data class SpeciesRef(
    val name: String? = null,
    val url: String? = null
)

internal data class Sprites(
    @SerializedName("front_default") val frontDefault: String?,
    @SerializedName("other") val other: OtherSprites
)

internal data class OtherSprites(
    @SerializedName("official-artwork") val officialArtwork: OfficialArtwork
)

internal data class OfficialArtwork(
    @SerializedName("front_default") val frontDefault: String?
)

internal data class TypeSlot(val type: Type)
internal data class Type(val name: String)

internal data class AbilitySlot(val ability: Ability)
internal data class Ability(val name: String)

internal data class StatSlot(val base_stat: Int, val stat: Stat)
internal data class Stat(val name: String)

internal data class MoveSlot(val move: Move)
internal data class Move(val name: String)

