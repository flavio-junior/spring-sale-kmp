package br.com.conding.tv.features.category.di

import br.com.conding.tv.features.category.data.api.CategoryApiService
import br.com.conding.tv.features.category.data.api.CategoryApiServiceImpl
import br.com.conding.tv.features.category.data.datasource.CategoryRemoteDataSource
import br.com.conding.tv.features.category.data.datasource.CategoryRemoteDataSourceImpl
import br.com.conding.tv.features.category.data.repository.CategoryRepository
import br.com.conding.tv.features.category.data.repository.CategoryRepositoryImpl
import br.com.conding.tv.features.category.domain.ConverterCategory
import br.com.conding.tv.features.category.ui.viewmodel.CategoryViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val categoryModule = module {
    single<CategoryApiService> { CategoryApiServiceImpl(httpClient = get(), localStorage = get()) }
    single<CategoryRemoteDataSource> { CategoryRemoteDataSourceImpl(categoryApiService = get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(categoryRemoteDataSource = get()) }
    single { ConverterCategory() }
    singleOf(::CategoryViewModel)
}
