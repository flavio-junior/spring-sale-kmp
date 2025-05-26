package br.com.conding.tv.features.product.domain

import br.com.conding.tv.features.category.domain.ConverterCategory
import br.com.conding.tv.features.others.converterPageableDTOToVO
import br.com.conding.tv.features.product.data.dto.ProductResponseDTO
import br.com.conding.tv.features.product.data.dto.ProductsResponseDTO
import br.com.conding.tv.features.product.data.vo.ProductResponseVO
import br.com.conding.tv.features.product.data.vo.ProductsResponseVO

class ConverterProduct(
    private val converter: ConverterCategory
) {

    fun converterContentDTOToVO(
        content: ProductsResponseDTO
    ): ProductsResponseVO {
        return ProductsResponseVO(
            totalPages = content.totalPages,
            content = converterProductsResponseDTOToVO(products = content.content),
            pageable = converterPageableDTOToVO(pageable = content.pageable)
        )
    }

    private fun converterProductsResponseDTOToVO(
        products: List<ProductResponseDTO>
    ): List<ProductResponseVO> {
        return products.map {
            ProductResponseVO(
                id = it.id,
                name = it.name,
                categories = converter.converterCategoriesResponseDTOToVO(
                    categories = it.categories ?: emptyList()
                ),
                price = it.price,
                quantity = it.quantity
            )
        }
    }
}
