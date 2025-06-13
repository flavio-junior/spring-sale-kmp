package br.com.conding.tv.features.settings.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.conding.tv.components.ui.Description
import br.com.conding.tv.components.ui.IconDefault
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.SimpleButton
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.features.settings.ui.viewmodel.SettingViewModel
import br.com.conding.tv.resources.GenericsStrings
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.WARNING
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE
import br.com.conding.tv.theme.Themes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DeleteCategoryBottomSheet(
    viewModel: SettingViewModel,
    onDismiss: () -> Unit = {}
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        containerColor = Themes.colors.background,
        onDismissRequest = onDismiss,
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier
                .background(color = Themes.colors.background)
                .padding(horizontal = Themes.size.spaceSize36),
            verticalArrangement = Arrangement.spacedBy(Themes.size.spaceSize24)
        ) {
            var observer: Triple<Boolean, Boolean, String?> by remember {
                mutableStateOf(Triple(first = false, second = false, third = EMPTY_TEXT))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconDefault(
                    iconName = IconName.BRAND_AWARENESS,
                    size = Themes.size.spaceSize48
                )
                Title(
                    label = WARNING,
                    backgroundTransparent = true,
                    modifier = Modifier.weight(weight = WEIGHT_SIZE),
                    textAlign = TextAlign.Start
                )
            }
            Description(
                label = GenericsStrings.WARNING_THE_DELETE_MY_ACCOUNT,
                backgroundTransparent = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Themes.size.spaceSize8)
            )
            LoadingButton(
                background = Themes.colors.success,
                onClick = {
                    observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                    viewModel.deleteMyAccount()
                },
                isEnabled = observer.first,
                label = GenericsStrings.YES
            )
            SimpleButton(
                onClick = onDismiss,
                label = GenericsStrings.NO,
                background = Themes.colors.error
            )
            Spacer(modifier = Modifier.size(Themes.size.spaceSize64))
        }
    }
}
