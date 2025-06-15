package br.com.conding.tv.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.conding.tv.components.factory.FilterFactory
import br.com.conding.tv.components.factory.currentItems
import br.com.conding.tv.resources.GenericStrings
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ZERO
import br.com.conding.tv.theme.Themes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FilterBottomSheet(
    onDismiss: (String?) -> Unit = {}
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    var itemFiltered: String by remember { mutableStateOf(value = FilterFactory.TEN_ITEMS) }
    ModalBottomSheet(
        containerColor = Themes.colors.background,
        onDismissRequest = { onDismiss(null) },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier
                .background(color = Themes.colors.background)
                .padding(horizontal = Themes.size.spaceSize36),
            verticalArrangement = Arrangement.spacedBy(Themes.size.spaceSize24)
        ) {
            SubTitle(
                label = GenericStrings.FILTER,
                backgroundTransparent = true,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            var itemSelected by remember {
                mutableStateOf(currentItems[NUMBER_ZERO])
            }
            currentItems.forEach { item ->
                CheckBox(
                    label = item,
                    isSelected = (item == itemSelected),
                    onCheckedChange = {
                        itemSelected = it
                        itemFiltered = it
                    }
                )
            }
            LoadingButton(
                onClick = {
                    onDismiss(itemFiltered)
                },
                label = GenericStrings.SAVE_MODIFIER
            )
            SimpleButton(
                onClick = {
                    onDismiss(null)
                },
                label = GenericStrings.CANCEL,
                background = Themes.colors.error
            )
            Spacer(modifier = Modifier.size(size = Themes.size.spaceSize64))
        }
    }
}
