package br.com.conding.tv.di

import br.com.conding.tv.resources.VersionApp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val androidModule = module {
    single { VersionApp(context = androidContext()) }
}
