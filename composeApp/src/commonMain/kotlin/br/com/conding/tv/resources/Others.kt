package br.com.conding.tv.resources

import br.com.conding.tv.theme.NumbersUtils.NUMBER_EIGHTY
import br.com.conding.tv.theme.NumbersUtils.NUMBER_FIFTY
import br.com.conding.tv.theme.NumbersUtils.NUMBER_FORTY
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ONE_HUNDRED
import br.com.conding.tv.theme.NumbersUtils.NUMBER_SIXTY
import br.com.conding.tv.theme.NumbersUtils.NUMBER_TEN
import br.com.conding.tv.theme.NumbersUtils.NUMBER_TWENTY

fun converterSizeStringToInt(size: String): Int {
    return when (size) {
        "10 Itens" -> NUMBER_TEN
        "20 Itens" -> NUMBER_TWENTY
        "40 Itens" -> NUMBER_FORTY
        "50 Itens" -> NUMBER_FIFTY
        "60 Itens" -> NUMBER_SIXTY
        "80 Itens" -> NUMBER_EIGHTY
        "100 Itens" -> NUMBER_ONE_HUNDRED
        else -> 60
    }
}
