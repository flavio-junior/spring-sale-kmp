package br.com.conding.tv.features.account.domain.converter

import br.com.conding.tv.features.account.data.dto.TokenResponseDTO
import br.com.conding.tv.features.account.data.vo.TokenResponseVO

class ConverterToken {

    fun converterTokenRequestDTOToTokenResponseVO(
        tokenResponseDTO: TokenResponseDTO
    ): TokenResponseVO {
        return TokenResponseVO(
            expiration = tokenResponseDTO.expiration,
            accessToken = tokenResponseDTO.accessToken,
            refreshToken = tokenResponseDTO.refreshToken
        )
    }
}
