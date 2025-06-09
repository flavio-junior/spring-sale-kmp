package br.com.conding.tv.features.product.di

import br.com.conding.tv.features.product.data.api.ProductApiService
import br.com.conding.tv.features.product.data.api.ProductApiServiceImpl
import br.com.conding.tv.features.product.data.datasource.ProductRemoteDataSource
import br.com.conding.tv.features.product.data.datasource.ProductRemoteDataSourceImpl
import br.com.conding.tv.features.product.data.repository.ProductRepository
import br.com.conding.tv.features.product.data.repository.ProductRepositoryImpl
import br.com.conding.tv.features.product.domain.ConverterProduct
import br.com.conding.tv.features.product.ui.viewmodel.ProductViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val productModule = module {
    single<ProductApiService> { ProductApiServiceImpl(httpClient = get(), localStorage = get()) }
    single<ProductRemoteDataSource> { ProductRemoteDataSourceImpl(productApiService = get()) }
    single<ProductRepository> { ProductRepositoryImpl(productRemoteDataSource = get()) }
    single { ConverterProduct(converter = get()) }
    singleOf(::ProductViewModel)
}
