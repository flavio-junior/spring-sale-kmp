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
import br.com.conding.tv.components.model.LocationRoute
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
    onSearch: (String, Int, String, LocationRoute) -> Unit,
    onSort: (String, Int, String, LocationRoute) -> Unit,
    onFilter: (String, Int, String, LocationRoute) -> Unit,
    onRefresh: (String, Int, String, LocationRoute) -> Unit
) {
    when (system) {
        TypeSystem.DESKTOP -> {
            HeaderSearchDesktop(
                modifier = modifier,
                onSearch = onSearch,
                onSort = onSort,
                onFilter = onFilter,
                onRefresh = onRefresh
            )
        }

        TypeSystem.MOBILE -> {
            HeaderSearchMobile(
                label = label,
                onSearch = onSearch,
                onSort = onSort
            )
        }
    }
}

@Composable
private fun HeaderSearchMobile(
    label: String? = null,
    onSearch: (String, Int, String, LocationRoute) -> Unit,
    onSort: (String, Int, String, LocationRoute) -> Unit
) {
    var name by remember { mutableStateOf(value = EMPTY_TEXT) }
    var sortBy: String by remember { mutableStateOf(value = ASC) }
    var size: String by remember { mutableStateOf(value = SIZE_DEFAULT) }
    var openBottomSheet: Boolean by remember { mutableStateOf(value = false) }
    ContentHorizontal(
        content = {
            Title(
                label = label ?: EMPTY_TEXT,
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
            onSearch(name, converterSizeStringToInt(size = size), sortBy, LocationRoute.SEARCH)
        }
    )
    ContentHorizontal(
        content = {
            SubTitle(
                label = RESULT,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(weight = WEIGHT_SIZE)
            )
            SortBy(
                onClick = {
                    sortBy = it
                    onSort(name, converterSizeStringToInt(size = size), sortBy, LocationRoute.SORT)
                }
            )
        }
    )
    if (openBottomSheet) {
        FilterBottomSheet(
            onDismiss = {
                openBottomSheet = false
                size = it ?: EMPTY_TEXT
                onSearch(name, converterSizeStringToInt(size = size), sortBy, LocationRoute.SEARCH)
            }
        )
    }
}

@Composable
private fun HeaderSearchDesktop(
    modifier: Modifier = Modifier,
    onSearch: (String, Int, String, LocationRoute) -> Unit,
    onSort: (String, Int, String, LocationRoute) -> Unit,
    onFilter: (String, Int, String, LocationRoute) -> Unit,
    onRefresh: (String, Int, String, LocationRoute) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        modifier = modifier.fillMaxWidth().wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var name: String by remember { mutableStateOf(value = EMPTY_TEXT) }
        var sortBy: String by remember { mutableStateOf(value = ASC) }
        var size: String by remember { mutableStateOf(value = SIZE_DEFAULT) }
        Search(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.weight(weight = WEIGHT_SIZE),
            onGo = {
                onSearch(name, converterSizeStringToInt(size = size), sortBy, LocationRoute.SEARCH)
            }
        )
        SortBy(
            onClick = {
                sortBy = it
                onSort(name, converterSizeStringToInt(size = size), sortBy, LocationRoute.SORT)
            }
        )
        DropdownMenu(
            selectedValue = size,
            items = currentItems,
            label = SIZE_LIST,
            onValueChangedEvent = {
                size = it
                onFilter(name, converterSizeStringToInt(size = size), sortBy, LocationRoute.FILTER)
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
                onRefresh(name, converterSizeStringToInt(size = size), sortBy, LocationRoute.RELOAD)
            }
        )
    }
}
