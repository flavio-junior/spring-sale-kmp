package br.com.conding.tv.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal val DarkColor = Colors(
    primary = Color(color = 0xFFA9B7C6),
    secondary = Color(color = 0xFFCC7832),
    background = Color(color = 0xFF2B2B2B),
    text = Color(color = 0xFFFFFFFF),
    success = Color(color = 0xFF6A8759),
    error = Color(color = 0xFFFF0000),
    disabled = Color(color = 0xFF545B62)
)

internal val LightColor = Colors(
    primary = Color(color = 0xFF010205),
    secondary = Color(color = 0xFF4a13b7),
    background = Color(color = 0xFFFF1F1F2),
    text = Color(color = 0xFFFFFFFF),
    success = Color(color = 0xFF197E10),
    error = Color(color = 0xFFCC1922),
    disabled = Color(color = 0xFF545B62)
)

internal object CommonColors {
    val ITEM_SELECTED = Color(color = 0xFF17202a)
}

internal class Colors(
    primary: Color,
    secondary: Color,
    background: Color,
    text: Color,
    success: Color,
    error: Color,
    disabled: Color
) {
    var primary by mutableStateOf(primary)
        private set

    var secondary by mutableStateOf(secondary)
        private set

    var success by mutableStateOf(success)
        private set

    var error by mutableStateOf(error)
        private set

    var disabled by mutableStateOf(disabled)
        private set

    var background by mutableStateOf(background)
        private set

    var text by mutableStateOf(text)
        private set

    fun copy(
        primary: Color = this.primary,
        secondary: Color = this.secondary,
        background: Color = this.background,
        success: Color = this.success,
        error: Color = this.error,
        text: Color = this.text,
        disabled: Color = this.disabled
    ) = Colors(
        primary = primary,
        secondary = secondary,
        background = background,
        text = text,
        success = success,
        error = error,
        disabled = disabled
    )

    fun updateColorsFrom(other: Colors) {
        primary = other.primary
        secondary = other.secondary
        background = other.background
        text = other.text
        success = other.success
        error = other.error
        disabled = other.disabled
    }
}

internal val LocalColors = staticCompositionLocalOf { LightColor }
