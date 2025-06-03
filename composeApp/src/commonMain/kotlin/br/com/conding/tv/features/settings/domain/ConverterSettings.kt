package br.com.conding.tv.features.settings.domain

import br.com.conding.tv.features.settings.data.dto.UserResponseDTO
import br.com.conding.tv.features.settings.data.vo.UserResponseVO

class ConverterSettings {

    fun converterUserResponseDTOToVO(
        userResponseDTO: UserResponseDTO? = null
    ): UserResponseVO {
        return UserResponseVO(
            id = userResponseDTO?.id,
            name = userResponseDTO?.name,
            surname = userResponseDTO?.surname,
            username = userResponseDTO?.username,
            email = userResponseDTO?.email
        )
    }
}
