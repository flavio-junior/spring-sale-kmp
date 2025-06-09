package br.com.conding.tv.networking.resources

internal enum class AlternativesRoutes {
    ERROR_401,
    ERROR_403,
    ERROR_404,
    ERROR_500,
    ERROR_503,
    ERROR_504
}

internal fun selectAlternativeRoute(
    code: Int
): AlternativesRoutes {
    return when (code) {
        401 -> AlternativesRoutes.ERROR_401
        403 -> AlternativesRoutes.ERROR_403
        404 -> AlternativesRoutes.ERROR_404
        500 -> AlternativesRoutes.ERROR_500
        else -> {
            AlternativesRoutes.ERROR_500
        }
    }
}
