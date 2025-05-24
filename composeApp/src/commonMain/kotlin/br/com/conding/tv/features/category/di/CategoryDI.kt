package br.com.conding.tv.features.category.di

import br.com.conding.tv.features.category.data.repository.CategoryRemoteDataSource
import br.com.conding.tv.features.category.data.repository.CategoryRepository
import br.com.digital.store.features.category.domain.ConverterCategory
import org.koin.dsl.module

val categoryModule = module {
    single { ConverterCategory() }
    single<CategoryRepository> {
        CategoryRemoteDataSource(httpClient = get(), get())
    }
    //singleOf(::CategoryViewModel)
}
