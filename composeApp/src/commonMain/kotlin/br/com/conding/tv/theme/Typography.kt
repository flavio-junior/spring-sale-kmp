package br.com.conding.tv.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import br.com.conding.tv.resources.backgroundTransparent
import br.com.conding.tv.resources.selectedItem
import org.jetbrains.compose.resources.Font
import springsale.composeapp.generated.resources.JetBrainsMono_Bold
import springsale.composeapp.generated.resources.JetBrainsMono_Medium
import springsale.composeapp.generated.resources.JetBrainsMono_Regular
import springsale.composeapp.generated.resources.Res

private val fontSize = FontSize

internal data class Typography(
    val selectedItem: Boolean = false,
    val backgroundTransparent: Boolean = false,
    val textAlign: TextAlign = TextAlign.Start,
    val typeFont: TypeFont? = null
) {
    @Composable
    fun title() = TextStyle(
        color = if (selectedItem) selectedItem(selectedItem = selectedItem) else backgroundTransparent(
            backgroundTransparent = backgroundTransparent
        ),
        fontSize = fontSize.fontSize32,
        fontWeight = FontWeight.SemiBold,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_BOLD),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )

    @Composable
    fun subTitle() = TextStyle(
        color = if (selectedItem) selectedItem(selectedItem = selectedItem) else backgroundTransparent(
            backgroundTransparent = backgroundTransparent
        ),
        fontSize = fontSize.fontSize24,
        fontWeight = FontWeight.Medium,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_MEDIUM),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )

    @Composable
    fun description() = TextStyle(
        color = if (selectedItem) selectedItem(selectedItem = selectedItem) else backgroundTransparent(
            backgroundTransparent = backgroundTransparent
        ),
        fontSize = fontSize.fontSize20,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_REGULAR),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )

    @Composable
    fun simpleText(): TextStyle = TextStyle(
        color = if (selectedItem) selectedItem(selectedItem = selectedItem) else backgroundTransparent(
            backgroundTransparent = backgroundTransparent
        ),
        fontSize = fontSize.fontSize16,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_REGULAR),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )

    @Composable
    fun infoText() = TextStyle(
        color = if (selectedItem) selectedItem(selectedItem = selectedItem) else backgroundTransparent(
            backgroundTransparent = backgroundTransparent
        ),
        fontSize = fontSize.fontSize14,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_BOLD),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )

    @Composable
    fun smallText() = TextStyle(
        color = if (selectedItem) selectedItem(selectedItem = selectedItem) else backgroundTransparent(
            backgroundTransparent = backgroundTransparent
        ),
        fontSize = fontSize.fontSize12,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_BOLD),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )

    @Composable
    fun miniText() = TextStyle(
        color = if (selectedItem) selectedItem(selectedItem = selectedItem) else backgroundTransparent(
            backgroundTransparent = backgroundTransparent
        ),
        fontSize = fontSize.fontSize8,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_BOLD),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )
}

internal val LocalTypography = staticCompositionLocalOf {
    Typography()
}

@Composable
private fun SelectFontFamily(typeFont: TypeFont): FontFamily {
    return when (typeFont) {
        TypeFont.ONEST_BOLD -> FontFamily(Font(Res.font.JetBrainsMono_Bold))
        TypeFont.ONEST_MEDIUM -> FontFamily(Font(Res.font.JetBrainsMono_Medium))
        TypeFont.ONEST_REGULAR -> FontFamily(Font(Res.font.JetBrainsMono_Regular))
    }
}
