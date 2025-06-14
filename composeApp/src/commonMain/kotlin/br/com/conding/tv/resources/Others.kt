package br.com.conding.tv.resources

import br.com.conding.tv.components.factory.FilterFactory
import br.com.conding.tv.theme.NumbersUtils.NUMBER_EIGHTY
import br.com.conding.tv.theme.NumbersUtils.NUMBER_FIFTY
import br.com.conding.tv.theme.NumbersUtils.NUMBER_FORTY
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ONE_HUNDRED
import br.com.conding.tv.theme.NumbersUtils.NUMBER_SIXTY
import br.com.conding.tv.theme.NumbersUtils.NUMBER_TEN
import br.com.conding.tv.theme.NumbersUtils.NUMBER_TWENTY

internal fun converterSizeStringToInt(size: String): Int? {
    return when (size) {
        FilterFactory.TEN_ITEMS -> NUMBER_TEN
        FilterFactory.TWENTY_ITEMS -> NUMBER_TWENTY
        FilterFactory.FORTY_ITEMS -> NUMBER_FORTY
        FilterFactory.FIFTY_ITEMS -> NUMBER_FIFTY
        FilterFactory.SIXTY_ITEMS -> NUMBER_SIXTY
        FilterFactory.EIGHTY_ITEMS -> NUMBER_EIGHTY
        FilterFactory.ONE_HUNDRED_ITEMS -> NUMBER_ONE_HUNDRED
        else -> null
    }
}
