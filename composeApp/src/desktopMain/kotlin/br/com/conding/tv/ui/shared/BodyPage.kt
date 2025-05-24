package br.com.conding.tv.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import br.com.conding.tv.theme.Themes
import br.com.conding.tv.utils.TypeLayout

@Composable
fun BodyPage(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    typeLayout: TypeLayout = TypeLayout.COLUMN,
    backgroundColor: Color = Themes.colors.background,
    body: @Composable () -> Unit = {}
) {
    when (typeLayout) {
        TypeLayout.BOX -> Box(
            contentAlignment = alignment,
            modifier = modifier
                .background(color = backgroundColor)
                .fillMaxSize()
        ) {
            body()
        }

        TypeLayout.COLUMN -> Column(
            modifier = modifier
                .background(color = backgroundColor)
                .fillMaxSize()
        ) {
            body()
        }

        TypeLayout.ROW -> Row(
            modifier = modifier
                .background(color = backgroundColor)
                .fillMaxSize()
        ) {
            body()
        }
    }
}
