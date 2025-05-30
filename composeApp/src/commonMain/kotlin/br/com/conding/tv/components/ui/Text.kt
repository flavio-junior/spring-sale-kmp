package br.com.conding.tv.components.ui

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import br.com.conding.tv.resources.GenericsStrings.PROJECT_NAME
import br.com.conding.tv.theme.Themes
import br.com.conding.tv.theme.TypeFont
import br.com.conding.tv.theme.Typography
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Title(
    modifier: Modifier = Modifier,
    color: Color = Themes.colors.primary,
    typeFont: TypeFont? = null,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    label: String
) {
    BasicText(
        text = label,
        modifier = modifier,
        style = Typography(color = color, textAlign = textAlign, typeFont = typeFont).title(),
        maxLines = maxLines
    )
}

@Composable
fun SubTitle(
    modifier: Modifier = Modifier,
    color: Color = Themes.colors.primary,
    label: String,
    typeFont: TypeFont? = null,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    BasicText(
        text = label,
        modifier = modifier,
        style = Typography(color = color, textAlign = textAlign, typeFont = typeFont).subTitle(),
        maxLines = maxLines
    )
}

@Composable
fun Description(
    modifier: Modifier = Modifier,
    label: String,
    color: Color = Themes.colors.primary,
    typeFont: TypeFont? = null,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    BasicText(
        text = label,
        modifier = modifier,
        style = Typography(color = color, textAlign = textAlign, typeFont = typeFont).description(),
        maxLines = maxLines
    )
}

@Composable
fun SimpleText(
    modifier: Modifier = Modifier,
    color: Color = Themes.colors.primary,
    label: String,
    typeFont: TypeFont? = null,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    BasicText(
        text = label,
        modifier = modifier,
        style = Typography(color = color, textAlign = textAlign, typeFont = typeFont).simpleText(),
        maxLines = maxLines
    )
}

@Composable
fun InfoText(
    modifier: Modifier = Modifier,
    color: Color = Themes.colors.primary,
    label: String,
    typeFont: TypeFont? = null,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    BasicText(
        text = label,
        modifier = modifier,
        style = Typography(color = color, textAlign = textAlign, typeFont = typeFont).infoText(),
        maxLines = maxLines
    )
}

@Composable
fun SmallText(
    modifier: Modifier = Modifier,
    color: Color = Themes.colors.primary,
    label: String,
    typeFont: TypeFont? = null,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    BasicText(
        text = label,
        modifier = modifier,
        style = Typography(color = color, textAlign = textAlign, typeFont = typeFont).smallText(),
        maxLines = maxLines
    )
}

@Composable
fun MiniText(
    modifier: Modifier = Modifier,
    color: Color = Themes.colors.primary,
    label: String,
    typeFont: TypeFont? = null,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    BasicText(
        text = label,
        modifier = modifier,
        style = Typography(color = color, textAlign = textAlign, typeFont = typeFont).miniText(),
        maxLines = maxLines
    )
}

@Composable
@Preview
private fun ShowTextPreview() {
    Title(label = PROJECT_NAME)
}

val LocalTextStyleDefault = compositionLocalOf(structuralEqualityPolicy()) { TextStyle.Default }

@Composable
fun ProvideTextStyleDefault(value: TextStyle, content: @Composable () -> Unit) {
    val mergedStyle: TextStyle = LocalTextStyleDefault.current.merge(value)
    CompositionLocalProvider(value = LocalTextStyleDefault provides mergedStyle, content = content)
}
