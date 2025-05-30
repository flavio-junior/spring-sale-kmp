package br.com.conding.tv

import android.app.Application
import br.com.conding.tv.di.commonModule
import br.com.conding.tv.features.account.di.accountModule
import br.com.conding.tv.features.category.di.categoryModule
import br.com.conding.tv.features.product.di.productModule
import br.com.conding.tv.networking.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApp)
            androidLogger(Level.INFO)
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
}
