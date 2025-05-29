package br.com.conding.tv.features.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.ui.ItemMenu
import br.com.conding.tv.components.ui.ObserveNetworkStateHandler
import br.com.conding.tv.features.account.ui.viewmodel.AccountViewModel
import br.com.conding.tv.features.home.factory.home
import br.com.conding.tv.features.shared.BodyPage
import br.com.conding.tv.navigation.NavigationItems
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.networking.resources.reloadViewModels
import br.com.conding.tv.theme.NumbersUtils.NUMBER_FOUR
import br.com.conding.tv.theme.Themes
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun HomeScreen(
    goToNextScreen: (String) -> Unit = {},
    goToLoginScreen: () -> Unit = {}
) {
    BodyPage(
        body = {
            var callViewModel: Boolean by remember { mutableStateOf(value = false) }
            var openDialog: Boolean by remember { mutableStateOf(value = false) }
            val viewModel: AccountViewModel = getKoin().get()
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(color = Themes.colors.background)
                    .fillMaxHeight()
                    .padding(all = Themes.size.spaceSize36)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(count = NUMBER_FOUR),
                    verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
                    horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
                ) {
                    items(home) { menu ->
                        ItemMenu(
                            menu = menu,
                            goToNextScreen = {
                                when (it) {
                                    NavigationItems.EXIT.name -> {
                                        openDialog = true
                                    }

                                    else -> {
                                        goToNextScreen(it)
                                    }
                                }
                            }
                        )
                    }
                }
                if (openDialog) {
                    ConfirmExitDialog(
                        onDismissRequest = {
                            callViewModel = true
                        },
                        onConfirmation = {
                            openDialog = false
                            //exitProcess(status = NUMBER_ZERO)
                        }
                    )
                }
                if (callViewModel) {
                    viewModel.cleanToken()
                    ObserveNetworkStateHandlerConfirmExitDialog(
                        viewModel = viewModel,
                        goToLoginScreen = {
                            openDialog = false
                            callViewModel = false
                            goToLoginScreen()
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun ObserveNetworkStateHandlerConfirmExitDialog(
    viewModel: AccountViewModel,
    goToLoginScreen: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.cleanToken }
    ObserveNetworkStateHandler(
        state = state,
        goToAlternativeRoutes = {},
        onSuccess = {
            reloadViewModels()
            goToLoginScreen()
        }
    )
}
