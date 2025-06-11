package br.com.conding.tv.resources

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import br.com.conding.tv.theme.CommonColors.ITEM_SELECTED
import br.com.conding.tv.theme.SpaceSize
import br.com.conding.tv.theme.Themes

@Composable
internal fun backgroundTransparent(
    backgroundTransparent: Boolean = false
): Color {
    return if (backgroundTransparent) {
        Color(color = Themes.colors.primary.toArgb())
    } else {
        Color(color = Themes.colors.text.toArgb())
    }
}

@Composable
internal fun selectedItem(
    selectedItem: Boolean = false
): Color {
    return if (selectedItem) {
        Color(color = Themes.colors.background.toArgb())
    } else {
        Color(color = ITEM_SELECTED.toArgb())
    }
}

internal fun Modifier.onClickable(
    onClick: () -> Unit
): Modifier {
    return this.then(
        other = Modifier.clickable(
            interactionSource = MutableInteractionSource(),
            indication = null,
            enabled = true,
            onClick = onClick,
            role = Role.Button
        )
    )
}

@Composable
internal fun Modifier.onSelectable(
    selected: Boolean,
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier {
    return this.then(
        other = Modifier.selectable(
            selected = selected,
            enabled = enabled,
            role = Role.RadioButton,
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
    )
}

internal fun Modifier.onBorder(
    onClick: () -> Unit = {},
    spaceSize: Dp,
    width: Dp,
    color: Color
): Modifier {
    return this.then(
        other = Modifier
            .border(
                width = width,
                color = color,
                shape = RoundedCornerShape(corner = CornerSize(size = spaceSize))
            )
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                enabled = true,
                onClick = onClick,
                role = Role.Button
            )
            .clip(shape = RoundedCornerShape(corner = CornerSize(size = spaceSize)))
    )
}

internal fun Modifier.onCircle(
    color: Color = Color.Black,
    shape: Shape = CircleShape,
    width: Dp = SpaceSize.spaceSize2
): Modifier {
    return this.then(
        other = Modifier
            .border(
                BorderStroke(width = width, color = color),
                shape
            )
            .clip(shape = shape)
    )
}

internal fun Modifier.onBorderDefault(
    color: Color = Color.Black,
    onClick: () -> Unit = {},
    spaceSize: Dp = SpaceSize.spaceSize10,
    width: Dp = SpaceSize.spaceSize2
): Modifier {
    return this.then(
        other = Modifier
            .border(width, color, RoundedCornerShape(corner = CornerSize(spaceSize)))
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                enabled = true,
                onClick = onClick,
                role = Role.Button
            )
            .clip(shape = RoundedCornerShape(corner = CornerSize(spaceSize)))
    )
}

@Composable
internal fun Modifier.scroll(scroll: Boolean): Modifier {
    return this.then(
        if (scroll) {
            Modifier.verticalScroll(state = rememberScrollState(), enabled = true)
        } else {
            Modifier
        }
    )
}

@Composable
internal fun Modifier.changeColor(enabled: Boolean = false): Modifier {
    return this.then(
        if (enabled) {
            Modifier.background(color = ITEM_SELECTED)
        } else {
            Modifier.background(color = Themes.colors.secondary)
        }
    )
}
