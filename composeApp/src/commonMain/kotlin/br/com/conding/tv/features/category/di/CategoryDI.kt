package br.com.conding.tv.features.category.di

import br.com.conding.tv.features.category.data.repository.CategoryRemoteDataSource
import br.com.conding.tv.features.category.data.repository.CategoryRepository
import br.com.conding.tv.features.category.domain.ConverterCategory
import br.com.conding.tv.features.category.ui.viewmodel.CategoryViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val categoryModule = module {
    single { ConverterCategory() }
    single<CategoryRepository> {
        CategoryRemoteDataSource(httpClient = get(), localStorage = get())
    }
    singleOf(::CategoryViewModel)
}
