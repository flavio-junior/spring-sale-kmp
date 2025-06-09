package br.com.conding.tv.networking.resources

import br.com.conding.tv.networking.resources.NetworkingUtils.COMMON_MODULES
import org.koin.mp.KoinPlatform.getKoin

internal fun reloadViewModels() {
    val koin = getKoin()
    koin.unloadModules(COMMON_MODULES)
    koin.loadModules(COMMON_MODULES)
}
