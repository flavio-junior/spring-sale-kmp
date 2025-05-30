package br.com.conding.tv.features.account.di

import br.com.conding.tv.features.account.data.repository.AccountRemoteDataSource
import br.com.conding.tv.features.account.data.repository.AccountRepository
import br.com.conding.tv.features.account.domain.converter.ConverterToken
import br.com.conding.tv.features.account.ui.viewmodel.AccountViewModel
import org.koin.dsl.module

val accountModule = module {
    single<AccountRepository> { AccountRemoteDataSource(httpClient = get()) }
    single { ConverterToken() }
    single { AccountViewModel(localStorage = get(), repository = get(), converter = get()) }
}
