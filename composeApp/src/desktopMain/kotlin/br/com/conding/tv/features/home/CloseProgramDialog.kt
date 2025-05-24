package br.com.conding.tv.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import br.com.conding.tv.components.ui.Description
import br.com.conding.tv.components.ui.IconDefault
import br.com.conding.tv.components.ui.SimpleButton
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.resources.GenericsStrings.CHANGE_TO_OTHER_ACCOUNT
import br.com.conding.tv.resources.GenericsStrings.EXIT
import br.com.conding.tv.resources.GenericsStrings.WARNING
import br.com.conding.tv.resources.GenericsStrings.YOUR_ACTION
import br.com.conding.tv.resources.onBorder
import br.com.conding.tv.theme.Themes
import br.com.conding.tv.utils.CommonUtils.WEIGHT_SIZE
import springsale.composeapp.generated.resources.Res
import springsale.composeapp.generated.resources.brand_awareness

@Composable
fun ConfirmExitDialog(
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
                    icon = Res.drawable.brand_awareness,
                    size = Themes.size.spaceSize48
                )
                Title(
                    title = WARNING,
                    modifier = Modifier.weight(weight = WEIGHT_SIZE),
                    textAlign = TextAlign.Start
                )
            }
            Description(
                description = YOUR_ACTION,
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
                    label = CHANGE_TO_OTHER_ACCOUNT,
                    background = Themes.colors.error,
                    modifier = Modifier.weight(weight = WEIGHT_SIZE)
                )
                SimpleButton(
                    onClick = onConfirmation,
                    label = EXIT,
                    modifier = Modifier.weight(weight = WEIGHT_SIZE)
                )
            }
        }
    }
}
