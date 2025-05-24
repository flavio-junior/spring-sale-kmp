package br.com.conding.tv.di

import br.com.conding.tv.data.repository.local.DesktopLocalStorage
import br.com.conding.tv.features.account.data.repository.LocalStorageImp
import br.com.conding.tv.viewmodel.ApiViewModel
import br.com.conding.tv.features.account.domain.converter.ConverterToken
import org.koin.dsl.module

val desktopModule = module {
    single { DesktopLocalStorage() }
    single<LocalStorageImp> { DesktopLocalStorage() }
    single { ConverterToken() }
    single { ApiViewModel(get(), get(), get()) }
}
