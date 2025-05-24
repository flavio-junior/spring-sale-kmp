package br.com.conding.tv.di

import br.com.conding.tv.features.account.viewmodel.AccountViewModel
import org.koin.dsl.module

val desktopModule = module {
    single { AccountViewModel(localStorage = get(), repository = get(), converter = get()) }
}
