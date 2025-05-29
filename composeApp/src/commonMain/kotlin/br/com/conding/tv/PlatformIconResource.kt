package br.com.conding.tv

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import br.com.conding.tv.resources.IconName

@Composable
expect fun getIconResource(iconName: IconName): Painter
