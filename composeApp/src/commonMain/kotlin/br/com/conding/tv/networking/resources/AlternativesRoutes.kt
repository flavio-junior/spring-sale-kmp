package br.com.conding.tv.networking.resources

internal enum class AlternativesRoutes {
    ERROR_401,
    ERROR_403,
    ERROR_404,
    ERROR_500,
    ERROR_503,
    ERROR_504,
    OTHER
}

internal fun selectAlternativeRoute(
    code: Int? = null,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    when (code) {
        401 -> goToAlternativeRoutes(AlternativesRoutes.ERROR_401)
        403 -> goToAlternativeRoutes(AlternativesRoutes.ERROR_403)
        404 -> goToAlternativeRoutes(AlternativesRoutes.ERROR_404)
        500 -> goToAlternativeRoutes(AlternativesRoutes.ERROR_500)
        else -> goToAlternativeRoutes(AlternativesRoutes.OTHER)
    }
}
