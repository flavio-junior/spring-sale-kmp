package br.com.conding.tv.features.others

import br.com.conding.tv.features.others.dto.PageableDTO
import br.com.conding.tv.features.others.vo.PageableVO

internal fun converterPageableDTOToVO(
    pageable: PageableDTO? = null
): PageableVO {
    return PageableVO(
        pageNumber = pageable?.pageNumber
    )
}
