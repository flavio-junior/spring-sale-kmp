package br.com.conding.tv.networking.resources

import br.com.conding.tv.features.account.di.accountModule
import br.com.conding.tv.features.category.di.categoryModule
import br.com.conding.tv.networking.di.networkModule

object NetworkingUtils {
    const val ERROR_TIMEOUT = "Conexão Perdida com o Servidor!"
    const val ERROR_UNKNOWN = "Error desconhecido"
    const val TIMEOUT_SIZE = 60000L
    val COMMON_MODULES = listOf(networkModule, accountModule, categoryModule)
}
