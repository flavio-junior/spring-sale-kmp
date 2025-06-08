package br.com.conding.tv.features.category.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import br.com.conding.tv.components.ui.IsErrorMessage
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.TextField
import br.com.conding.tv.features.category.ui.viewmodel.CategoryViewModel
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.networking.resources.reloadViewModels
import br.com.conding.tv.resources.GenericsStrings.CATEGORY_NAME
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.NOT_BLANK_OR_EMPTY
import br.com.conding.tv.resources.GenericsStrings.SAVE_CATEGORY
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE_2
import br.com.conding.tv.resources.checkNameIsNull
import br.com.conding.tv.theme.Themes
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun SaveCategory(
    modifier: Modifier = Modifier,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    var observer: Triple<Boolean, Boolean, String?> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    Column(
        modifier = modifier.padding(top = Themes.size.spaceSize8),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val viewModel: CategoryViewModel = getKoin().get()
            var categoryName by remember { mutableStateOf(value = EMPTY_TEXT) }
            val saveCategory = { category: String ->
                if (checkNameIsNull(name = category)) {
                    observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
                } else {
                    observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                    viewModel.createCategory(category = category)
                }
            }
            TextField(
                label = CATEGORY_NAME,
                value = categoryName,
                iconName = IconName.EDIT,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Go,
                isError = observer.second,
                onValueChange = { categoryName = it },
                modifier = modifier.weight(weight = WEIGHT_SIZE_2),
                onGo = {
                    saveCategory(categoryName)
                }
            )
            LoadingButton(
                label = SAVE_CATEGORY,
                onClick = {
                    saveCategory(categoryName)
                },
                isEnabled = observer.first,
                modifier = modifier.weight(weight = WEIGHT_SIZE)
            )
            UiResponseCreateNewCategoryScreen(
                viewModel = viewModel,
                onError = {
                    observer = it
                },
                goToAlternativeRoutes = {
                    goToAlternativeRoutes(it)
                    reloadViewModels()
                },
                onSuccessful = {
                    categoryName = EMPTY_TEXT
                }
            )
        }
        IsErrorMessage(isError = observer.second, message = observer.third ?: EMPTY_TEXT)
    }
}
