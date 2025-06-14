package br.com.conding.tv.components.factory

internal object FilterFactory {
    const val TEN_ITEMS = "10 Itens"
    const val TWENTY_ITEMS = "20 Itens"
    const val FORTY_ITEMS = "40 Itens"
    const val FIFTY_ITEMS = "50 Itens"
    const val SIXTY_ITEMS = "60 Itens"
    const val EIGHTY_ITEMS = "80 Itens"
    const val ONE_HUNDRED_ITEMS = "100 Itens"
}

internal val currentItems = listOf(
    FilterFactory.TEN_ITEMS,
    FilterFactory.TWENTY_ITEMS,
    FilterFactory.FORTY_ITEMS,
    FilterFactory.FIFTY_ITEMS,
    FilterFactory.SIXTY_ITEMS,
    FilterFactory.EIGHTY_ITEMS,
    FilterFactory.ONE_HUNDRED_ITEMS
)
