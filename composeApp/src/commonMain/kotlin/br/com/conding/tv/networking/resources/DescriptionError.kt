package br.com.conding.tv.networking.resources

internal data class DescriptionError(
    val code: Int? = null,
    val type: ErrorType = ErrorType.CLIENT,
    val message: String? = null
)
