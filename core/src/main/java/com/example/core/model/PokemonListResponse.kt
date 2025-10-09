package com.example.core.model

import com.google.gson.annotations.SerializedName

data class PokemonListResponse(
    val results: List<PokemonResult>? = null
)

data class PokemonResult(
    val name: String? = null,
    val url: String? = null
)

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val types: List<TypeSlot>,
    val abilities: List<AbilitySlot>,
    val stats: List<StatSlot>,
    val height: Int,
    val weight: Int,
    val moves: List<MoveSlot>
)

data class Sprites(
    @SerializedName("front_default") val frontDefault: String?,
    @SerializedName("other") val other: OtherSprites
)

data class OtherSprites(
    @SerializedName("official-artwork") val officialArtwork: OfficialArtwork
)

data class OfficialArtwork(
    @SerializedName("front_default") val frontDefault: String?
)

data class TypeSlot(val type: Type)
data class Type(val name: String)

data class AbilitySlot(val ability: Ability)
data class Ability(val name: String)

data class StatSlot(val base_stat: Int, val stat: Stat)
data class Stat(val name: String)

data class MoveSlot(val move: Move)
data class Move(val name: String)

