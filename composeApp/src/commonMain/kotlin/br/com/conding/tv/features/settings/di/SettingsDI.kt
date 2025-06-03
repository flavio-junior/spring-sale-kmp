package br.com.conding.tv.features.settings.di

import br.com.conding.tv.features.settings.data.api.SettingsApiService
import br.com.conding.tv.features.settings.data.api.SettingsApiServiceImpl
import br.com.conding.tv.features.settings.data.datasource.SettingsRemoteDataSource
import br.com.conding.tv.features.settings.data.datasource.SettingsRemoteDataSourceImpl
import br.com.conding.tv.features.settings.data.repository.SettingsRepository
import br.com.conding.tv.features.settings.data.repository.SettingsRepositoryImpl
import br.com.conding.tv.features.settings.domain.ConverterSettings
import br.com.conding.tv.features.settings.ui.viewmodel.SettingViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val settingsModule = module {
    single<SettingsApiService> { SettingsApiServiceImpl(httpClient = get(), localStorage = get()) }
    single<SettingsRemoteDataSource> { SettingsRemoteDataSourceImpl(settingsApiService = get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(settingsRemoteDataSource = get()) }
    single { ConverterSettings() }
    singleOf(::SettingViewModel)
}
