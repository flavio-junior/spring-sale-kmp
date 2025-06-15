package br.com.conding.tv.components.ui

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import br.com.conding.tv.resources.GenericStrings.PROJECT_NAME
import br.com.conding.tv.theme.TypeFont
import br.com.conding.tv.theme.Typography
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun Title(
    modifier: Modifier = Modifier,
    label: String,
    selectedItem: Boolean = false,
    backgroundTransparent: Boolean = false,
    typeFont: TypeFont? = null,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
) {
    BasicText(
        text = label,
        modifier = modifier,
        style = Typography(
            selectedItem = selectedItem,
            backgroundTransparent = backgroundTransparent,
            textAlign = textAlign,
            typeFont = typeFont
        ).title(),
        maxLines = maxLines
    )
}

@Composable
internal fun SubTitle(
    modifier: Modifier = Modifier,
    label: String,
    selectedItem: Boolean = false,
    backgroundTransparent: Boolean = false,
    typeFont: TypeFont? = null,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    BasicText(
        text = label,
        modifier = modifier,
        style = Typography(
            selectedItem = selectedItem,
            backgroundTransparent = backgroundTransparent,
            textAlign = textAlign,
            typeFont = typeFont
        ).subTitle(),
        maxLines = maxLines
    )
}

@Composable
internal fun Description(
    modifier: Modifier = Modifier,
    label: String,
    selectedItem: Boolean = false,
    backgroundTransparent: Boolean = false,
    typeFont: TypeFont? = null,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    BasicText(
        text = label,
        modifier = modifier,
        style = Typography(
            selectedItem = selectedItem,
            backgroundTransparent = backgroundTransparent,
            textAlign = textAlign,
            typeFont = typeFont
        ).description(),
        maxLines = maxLines
    )
}

@Composable
internal fun SimpleText(
    modifier: Modifier = Modifier,
    label: String,
    selectedItem: Boolean = false,
    backgroundTransparent: Boolean = false,
    typeFont: TypeFont? = null,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    BasicText(
        text = label,
        modifier = modifier,
        style = Typography(
            selectedItem = selectedItem,
            backgroundTransparent = backgroundTransparent,
            textAlign = textAlign,
            typeFont = typeFont
        ).simpleText(),
        maxLines = maxLines
    )
}

@Composable
internal fun InfoText(
    modifier: Modifier = Modifier,
    label: String,
    selectedItem: Boolean = false,
    backgroundTransparent: Boolean = false,
    typeFont: TypeFont? = null,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    BasicText(
        text = label,
        modifier = modifier,
        style = Typography(
            selectedItem = selectedItem,
            backgroundTransparent = backgroundTransparent,
            textAlign = textAlign,
            typeFont = typeFont
        ).infoText(),
        maxLines = maxLines
    )
}

@Composable
internal fun SmallText(
    modifier: Modifier = Modifier,
    label: String,
    selectedItem: Boolean = false,
    backgroundTransparent: Boolean = false,
    typeFont: TypeFont? = null,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    BasicText(
        text = label,
        modifier = modifier,
        style = Typography(
            selectedItem = selectedItem,
            backgroundTransparent = backgroundTransparent,
            textAlign = textAlign,
            typeFont = typeFont
        ).smallText(),
        maxLines = maxLines
    )
}

@Composable
internal fun MiniText(
    modifier: Modifier = Modifier,
    label: String,
    selectedItem: Boolean = false,
    backgroundTransparent: Boolean = false,
    typeFont: TypeFont? = null,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    BasicText(
        text = label,
        modifier = modifier,
        style = Typography(
            selectedItem = selectedItem,
            backgroundTransparent = backgroundTransparent,
            textAlign = textAlign,
            typeFont = typeFont
        ).miniText(),
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
internal fun ProvideTextStyleDefault(value: TextStyle, content: @Composable () -> Unit) {
    val mergedStyle: TextStyle = LocalTextStyleDefault.current.merge(value)
    CompositionLocalProvider(value = LocalTextStyleDefault provides mergedStyle, content = content)
}
