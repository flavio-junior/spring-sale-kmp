package br.com.conding.tv.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import br.com.conding.tv.resources.GenericsStrings.ALERT
import br.com.conding.tv.resources.GenericsStrings.CANCEL
import br.com.conding.tv.resources.GenericsStrings.CONFIRM
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE
import br.com.conding.tv.resources.onBorder
import br.com.conding.tv.theme.Themes

@Composable
fun Alert(
    label: String,
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {}
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .onBorder(
                    onClick = {},
                    spaceSize = Themes.size.spaceSize16,
                    width = Themes.size.spaceSize2,
                    color = Themes.colors.primary
                )
                .background(color = Themes.colors.background)
                .size(width = Themes.size.spaceSize400, height = Themes.size.spaceSize200)
                .padding(all = Themes.size.spaceSize16),
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconDefault(
                    iconName = IconName.BRAND_AWARENESS,
                    size = Themes.size.spaceSize48
                )
                Title(
                    title = ALERT,
                    modifier = Modifier.weight(weight = WEIGHT_SIZE),
                    textAlign = TextAlign.Start
                )
            }
            Description(
                description = label,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Themes.size.spaceSize8)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
            ) {
                SimpleButton(
                    onClick = onDismissRequest,
                    label = CANCEL,
                    background = Themes.colors.error,
                    modifier = Modifier.weight(weight = WEIGHT_SIZE)
                )
                SimpleButton(
                    onClick = onConfirmation,
                    label = CONFIRM,
                    modifier = Modifier.weight(weight = WEIGHT_SIZE)
                )
            }
        }
    }
}
