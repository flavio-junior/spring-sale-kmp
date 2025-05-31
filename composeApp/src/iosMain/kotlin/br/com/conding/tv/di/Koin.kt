package br.com.conding.tv.di

import br.com.conding.tv.features.account.di.accountModule
import br.com.conding.tv.features.category.di.categoryModule
import br.com.conding.tv.features.product.di.productModule
import br.com.conding.tv.networking.di.networkModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            modules = listOf(
                commonModule,
                accountModule,
                categoryModule,
                networkModule,
                productModule
            )
        )
    }
}