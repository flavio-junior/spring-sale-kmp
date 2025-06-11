package br.com.conding.tv.features.category.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.ui.Description
import br.com.conding.tv.features.category.data.vo.CategoryResponseVO
import br.com.conding.tv.features.category.ui.viewmodel.CategoryViewModel
import br.com.conding.tv.getIconResource
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE
import br.com.conding.tv.resources.onBorder
import br.com.conding.tv.resources.onClickable
import br.com.conding.tv.theme.Themes

@Composable
internal fun MobileCategoryItem(
    viewModel: CategoryViewModel,
    categoryResponseVO: CategoryResponseVO
) {
    var updateCategory: Boolean by remember { mutableStateOf(value = false) }
    var deleteCategory: Boolean by remember { mutableStateOf(value = false) }
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .onBorder(
                spaceSize = Themes.size.spaceSize12,
                width = Themes.size.spaceSize2,
                color = Themes.colors.primary
            )
            .fillMaxWidth()
            .padding(all = Themes.size.spaceSize12)
    ) {
        Description(
            label = categoryResponseVO.name,
            backgroundTransparent = true,
            modifier = Modifier
                .onClickable(
                    onClick = {
                        updateCategory = true
                    }
                )
                .padding(all = Themes.size.spaceSize12)
                .weight(weight = WEIGHT_SIZE)
        )
        Icon(
            painter = getIconResource(iconName = IconName.DELETE),
            contentDescription = IconName.DELETE.name,
            modifier = Modifier
                .onClickable(
                    onClick = {
                        deleteCategory = true
                    }
                )
                .size(size = Themes.size.spaceSize48),
            tint = Themes.colors.primary
        )
    }
    if (deleteCategory) {
        DeleteCategoryBottomSheet(
            viewModel = viewModel,
            category = categoryResponseVO,
            onDismiss = {
                deleteCategory = false
            }
        )
    }
    if (updateCategory) {
        UpdateCategoryBottomSheet(
            viewModel = viewModel,
            category = categoryResponseVO,
            onDismiss = {
                updateCategory = false
            }
        )
    }
}
