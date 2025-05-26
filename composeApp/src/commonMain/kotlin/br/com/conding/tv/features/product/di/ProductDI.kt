package br.com.conding.tv.features.product.di

import br.com.conding.tv.features.product.data.repository.ProductRemoteDataSource
import br.com.conding.tv.features.product.data.repository.ProductRepository
import br.com.conding.tv.features.product.domain.ConverterProduct
import br.com.conding.tv.features.product.ui.viewmodel.ProductViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val productModule = module {
    single { ConverterProduct(get()) }
    single<ProductRepository> {
        ProductRemoteDataSource(httpClient = get(), get())
    }
    singleOf(::ProductViewModel)
}
