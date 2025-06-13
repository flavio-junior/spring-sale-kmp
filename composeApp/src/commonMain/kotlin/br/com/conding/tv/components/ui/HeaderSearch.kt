package br.com.conding.tv.components.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.settings.TypeSystem
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.RESULT
import br.com.conding.tv.resources.GenericsStrings.SIZE_LIST
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.Settings.ASC
import br.com.conding.tv.resources.Settings.SIZE_DEFAULT
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE
import br.com.conding.tv.resources.converterSizeStringToInt
import br.com.conding.tv.resources.onBorder
import br.com.conding.tv.resources.currentItems
import br.com.conding.tv.theme.Themes

@Composable
internal fun HeaderSearch(
    modifier: Modifier = Modifier,
    label: String? = null,
    system: TypeSystem = TypeSystem.DESKTOP,
    filter: (String, Int, String) -> Unit
) {
    when (system) {
        TypeSystem.DESKTOP -> {
            HeaderSearchDesktop(modifier = modifier, filter = filter)
        }

        TypeSystem.MOBILE -> {
            HeaderSearchMobile(label = label, filter = filter)
        }
    }
}

@Composable
private fun HeaderSearchMobile(
    label: String? = null,
    filter: (String, Int, String) -> Unit,
) {
    var name by remember { mutableStateOf(value = EMPTY_TEXT) }
    var sortBy: String by remember { mutableStateOf(value = ASC) }
    var size: String by remember { mutableStateOf(value = SIZE_DEFAULT) }
    var openBottomSheet: Boolean by remember { mutableStateOf(value = false) }
    val callFilter = {
        converterSizeStringToInt(size = size)?.let {
            filter(name, it, sortBy)
        }
    }
    ContentHorizontal(
        content = {
            Title(
                label = label ?: EMPTY_TEXT,
                backgroundTransparent = true,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(weight = WEIGHT_SIZE)
            )
            IconDefault(
                iconName = IconName.FILTER,
                modifier = Modifier
                    .onBorder(
                        onClick = {},
                        color = Themes.colors.primary,
                        spaceSize = Themes.size.spaceSize16,
                        width = Themes.size.spaceSize2
                    )
                    .size(size = Themes.size.spaceSize64)
                    .padding(all = Themes.size.spaceSize8),
                onClick = {
                    openBottomSheet = true
                }
            )
        }
    )
    Search(
        value = name,
        onValueChange = {
            name = it
        },
        onGo = {
            callFilter()
        }
    )
    ContentHorizontal(
        content = {
            SubTitle(
                label = RESULT,
                backgroundTransparent = true,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(weight = WEIGHT_SIZE)
            )
            SortBy(
                onClick = {
                    sortBy = it
                    callFilter()
                }
            )
        }
    )
    if (openBottomSheet) {
        FilterBottomSheet(
            onDismiss = {
                openBottomSheet = false
                size = it ?: EMPTY_TEXT
                callFilter()
            }
        )
    }
}

@Composable
private fun HeaderSearchDesktop(
    modifier: Modifier = Modifier,
    filter: (String, Int, String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        modifier = modifier.fillMaxWidth().wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var name: String by remember { mutableStateOf(value = EMPTY_TEXT) }
        var sortBy: String by remember { mutableStateOf(value = ASC) }
        var size: String by remember { mutableStateOf(value = SIZE_DEFAULT) }
        val callFilter = {
            converterSizeStringToInt(size = size)?.let {
                filter(name, it, sortBy)
            }
        }
        Search(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.weight(weight = WEIGHT_SIZE),
            onGo = {
                callFilter()
            }
        )
        SortBy(
            onClick = {
                sortBy = it
                callFilter()
            }
        )
        DropdownMenu(
            selectedValue = size,
            items = currentItems,
            label = SIZE_LIST,
            onValueChangedEvent = {
                size = it
                callFilter()
            }
        )
        IconDefault(
            iconName = IconName.REFRESH,
            modifier = Modifier
                .onBorder(
                    onClick = {},
                    color = Themes.colors.primary,
                    spaceSize = Themes.size.spaceSize16,
                    width = Themes.size.spaceSize2
                )
                .size(size = Themes.size.spaceSize64)
                .padding(all = Themes.size.spaceSize8),
            onClick = {
                callFilter()
            }
        )
    }
}
