package br.com.conding.tv.features.account.di

import br.com.conding.tv.features.account.data.api.AccountApiService
import br.com.conding.tv.features.account.data.api.AccountApiServiceImpl
import br.com.conding.tv.features.account.data.datasource.remote.AccountRemoteDataSource
import br.com.conding.tv.features.account.data.datasource.remote.AccountRemoteDataSourceImpl
import br.com.conding.tv.features.account.data.repository.AccountRepository
import br.com.conding.tv.features.account.data.repository.AccountRepositoryImpl
import br.com.conding.tv.features.account.domain.converter.ConverterAccount
import br.com.conding.tv.features.account.ui.viewmodel.AccountViewModel
import org.koin.dsl.module

val accountModule = module {
    single<AccountApiService> { AccountApiServiceImpl(httpClient = get()) }
    single<AccountRemoteDataSource> { AccountRemoteDataSourceImpl(accountApiService = get()) }
    single<AccountRepository> { AccountRepositoryImpl(accountRemoteDataSource = get()) }
    single { ConverterAccount() }
    single { AccountViewModel(localStorage = get(), repository = get(), converter = get()) }
}
