package br.com.conding.tv.features.account.di

import br.com.conding.tv.features.account.data.repository.AccountRemoteDataSource
import br.com.conding.tv.features.account.data.repository.AccountRepository
import br.com.conding.tv.features.account.data.repository.LocalStorageImp
import br.com.conding.tv.features.account.domain.converter.ConverterToken
import br.com.conding.tv.networking.storage.LocalStorage
import org.koin.dsl.module

val accountModule = module {
    single { LocalStorage(dataStore = get()) }
    single<LocalStorageImp> { LocalStorage(dataStore = get()) }
    single<AccountRepository> { AccountRemoteDataSource(httpClient = get()) }
    single { ConverterToken() }
}
