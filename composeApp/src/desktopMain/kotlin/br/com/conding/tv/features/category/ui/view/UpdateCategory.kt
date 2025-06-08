package br.com.conding.tv.features.category.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.input.KeyboardType
import br.com.conding.tv.components.ui.Alert
import br.com.conding.tv.components.ui.IconDefault
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.TextField
import br.com.conding.tv.features.category.data.dto.UpdateCategoryRequestDTO
import br.com.conding.tv.features.category.data.vo.CategoryResponseVO
import br.com.conding.tv.features.category.ui.viewmodel.CategoryViewModel
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.resources.GenericsStrings.ACTUAL_NAME
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.ID
import br.com.conding.tv.resources.GenericsStrings.NEW_NAME_CATEGORY
import br.com.conding.tv.resources.GenericsStrings.NOT_BLANK_OR_EMPTY
import br.com.conding.tv.resources.GenericsStrings.UPDATE_CATEGORY
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE_2
import br.com.conding.tv.resources.checkNameIsNull
import br.com.conding.tv.resources.onBorder
import br.com.conding.tv.theme.Themes
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun UpdateCategory(
    modifier: Modifier = Modifier,
    categoryVO: CategoryResponseVO,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onCleanCategory: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        val viewModel: CategoryViewModel = getKoin().get()
        var observer: Triple<Boolean, Boolean, String?> by remember {
            mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
        }
        var openDialog by remember { mutableStateOf(value = false) }
        var categoryName by remember { mutableStateOf(value = EMPTY_TEXT) }
        val updateCategory = { category: String ->
            if (checkNameIsNull(name = category)) {
                observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
            } else {
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                viewModel.updateCategory(
                    category = UpdateCategoryRequestDTO(id = categoryVO.id, name = category)
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                enabled = false,
                label = ID,
                value = categoryVO.id.toString(),
                modifier = Modifier.weight(weight = WEIGHT_SIZE),
            )
            TextField(
                enabled = false,
                label = ACTUAL_NAME,
                value = categoryVO.name,
                modifier = Modifier.weight(weight = WEIGHT_SIZE_2),
            )
            IconDefault(
                iconName = IconName.CLOSE,
                modifier = Modifier
                    .onBorder(
                        onClick = {},
                        color = Themes.colors.primary,
                        spaceSize = Themes.size.spaceSize16,
                        width = Themes.size.spaceSize2
                    )
                    .size(size = Themes.size.spaceSize64)
                    .padding(all = Themes.size.spaceSize8),
                onClick = onCleanCategory
            )
        }
        TextField(
            label = NEW_NAME_CATEGORY,
            value = categoryName,
            iconName = IconName.EDIT,
            keyboardType = KeyboardType.Text,
            isError = observer.second,
            message = observer.third ?: EMPTY_TEXT,
            onValueChange = {
                categoryName = it
            },
            onGo = {
                openDialog = true
            }
        )
        LoadingButton(
            label = UPDATE_CATEGORY,
            onClick = {
                openDialog = true
            },
            isEnabled = observer.first
        )
        if (openDialog) {
            Alert(
                label = "$UPDATE_CATEGORY?",
                onDismissRequest = {
                    openDialog = false
                },
                onConfirmation = {
                    observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                    updateCategory(categoryName)
                    openDialog = false
                }
            )
        }
        UiResponseUpdateCategoryScreen(
            viewModel = viewModel,
            onError = {
                observer = it
            },
            goToAlternativeRoutes = goToAlternativeRoutes,
            onSuccessful = {
                CategoryResponseVO(id = 0, name = EMPTY_TEXT)
                categoryName = EMPTY_TEXT
            }
        )
        DeleteCategory(
            viewModel = viewModel,
            id = categoryVO.id,
            modifier = Modifier,
            onError = {
                observer = it
            },
            goToAlternativeRoutes = goToAlternativeRoutes,
            onSuccessful = {
                CategoryResponseVO(id = 0, name = EMPTY_TEXT)
            }
        )
    }
}
