package com.example.core.ui.utils

import android.graphics.Color

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
        fun fromString(colorName: String?): PokemonSpeciesColor {
            return entries.firstOrNull { it.name.equals(colorName, ignoreCase = true) } ?: WHITE
        }
    }
}
