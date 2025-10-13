package com.example.core.utils

import android.graphics.Color

enum class PokemonType(val color: Int) {
    FIRE(Color.parseColor("#F08030")),
    WATER(Color.parseColor("#6890F0")),
    GRASS(Color.parseColor("#78C850")),
    ELECTRIC(Color.parseColor("#F8D030")),
    ICE(Color.parseColor("#98D8D8")),
    FIGHTING(Color.parseColor("#C03028")),
    POISON(Color.parseColor("#A040A0")),
    GROUND(Color.parseColor("#E0C068")),
    FLYING(Color.parseColor("#A890F0")),
    PSYCHIC(Color.parseColor("#F85888")),
    BUG(Color.parseColor("#A8B820")),
    ROCK(Color.parseColor("#B8A038")),
    GHOST(Color.parseColor("#705898")),
    DRAGON(Color.parseColor("#7038F8")),
    DARK(Color.parseColor("#705848")),
    STEEL(Color.parseColor("#B8B8D0")),
    NORMAL(Color.GRAY);

    companion object {
        fun fromString(type: String): PokemonType {
            return entries.firstOrNull { it.name.equals(type, ignoreCase = true) } ?: NORMAL
        }
    }
}

enum class PokemonSpeciesColor(val color: Int) {
    BLACK(Color.parseColor("#2C2C2C")),
    BLUE(Color.parseColor("#7098AA")),
    BROWN(Color.parseColor("#A08C78")),
    GRAY(Color.parseColor("#8C8C8C")),
    GREEN(Color.parseColor("#66AA66")),
    PINK(Color.parseColor("#D0A0B0")),
    PURPLE(Color.parseColor("#8A5DAA")),
    RED(Color.parseColor("#C06060")),
    WHITE(Color.parseColor("#E8E8E8")),
    YELLOW(Color.parseColor("#D8C060"));

    companion object {
        fun fromString(colorName: String): PokemonSpeciesColor {
            return entries.firstOrNull { it.name.equals(colorName, ignoreCase = true) } ?: WHITE
        }
    }
}