package br.com.conding.tv.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val DarkColor = Colors(
    primary = Color(color = 0xFFA9B7C6),
    secondary = Color(color = 0xFFCC7832),
    background = Color(color = 0xFF2B2B2B),
    success = Color(color = 0xFF6A8759),
    error = Color(color = 0xFFFF0000)
)

val LightColor = Colors(
    primary = Color(color = 0xFF010205),
    secondary = Color(color = 0xFFCC7832),
    background = Color(color = 0xFFFF1F1F2),
    success = Color(color = 0xFF197E10),
    error = Color(color = 0xFFCC1922)
)

object CommonColors {
    val ITEM_SELECTED = Color(color = 0xFF17202a)
}

class Colors(
    primary: Color,
    secondary: Color,
    background: Color,
    success: Color,
    error: Color,
) {
    var primary by mutableStateOf(primary)
        private set

    var secondary by mutableStateOf(secondary)
        private set

    var success by mutableStateOf(success)
        private set

    var error by mutableStateOf(error)
        private set

    var background by mutableStateOf(background)
        private set

    fun copy(
        primary: Color = this.primary,
        secondary: Color = this.secondary,
        background: Color = this.background,
        success: Color = this.success,
        error: Color = this.error
    ) = Colors(
        primary = primary,
        secondary = secondary,
        background = background,
        success = success,
        error = error
    )

    fun updateColorsFrom(other: Colors) {
        primary = other.primary
        secondary = other.secondary
        success = other.success
        background = other.background
        error = other.error
    }
}

val LocalColors = staticCompositionLocalOf { LightColor }
