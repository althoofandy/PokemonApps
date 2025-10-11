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
    BLACK(Color.parseColor("#A9A9A9")),
    BLUE(Color.parseColor("#ADD8E6")),
    BROWN(Color.parseColor("#D2B48C")),
    GRAY(Color.parseColor("#C0C0C0")),
    GREEN(Color.parseColor("#98FB98")),
    PINK(Color.parseColor("#FFB6C1")),
    PURPLE(Color.parseColor("#A040A0")),
    RED(Color.parseColor("#FFA07A")),
    WHITE(Color.parseColor("#F5F5F5")),
    YELLOW(Color.parseColor("#F8D030"));

    companion object {
        fun fromString(colorName: String): PokemonSpeciesColor {
            return entries.firstOrNull { it.name.equals(colorName, ignoreCase = true) } ?: WHITE
        }
    }
}


