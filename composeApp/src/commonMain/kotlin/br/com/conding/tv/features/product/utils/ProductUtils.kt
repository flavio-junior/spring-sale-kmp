package br.com.conding.tv.features.product.utils

internal fun checkBodyProductIsNull(
    name: String,
    price: Double,
    quantity: Int
): Boolean {
    return name.isEmpty() && price == 0.0 && quantity == 0
}
