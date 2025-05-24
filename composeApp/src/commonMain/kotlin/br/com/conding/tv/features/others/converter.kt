package br.com.conding.tv.features.others

import br.com.conding.tv.features.others.dto.PageableDTO
import br.com.conding.tv.features.others.vo.PageableVO

fun converterPageableDTOToVO(
    pageable: PageableDTO
): PageableVO {
    return PageableVO(
        pageNumber = pageable.pageNumber
    )
}
