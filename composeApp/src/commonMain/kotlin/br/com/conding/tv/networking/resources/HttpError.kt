package br.com.conding.tv.networking.resources

internal enum class HttpError {
    ERROR_401,
    ERROR_403,
    ERROR_404,
    ERROR_500,
    ERROR_501,
    ERROR_503,
    ERROR_504,
    OTHER
}

internal fun determineErrorRoute(
    code: Int? = null,
    goToAlternativeRoutes: (HttpError) -> Unit = {}
) {
    when (code) {
        401 -> goToAlternativeRoutes(HttpError.ERROR_401)
        403 -> goToAlternativeRoutes(HttpError.ERROR_403)
        404 -> goToAlternativeRoutes(HttpError.ERROR_404)
        500 -> goToAlternativeRoutes(HttpError.ERROR_500)
        501 -> goToAlternativeRoutes(HttpError.ERROR_501)
        503 -> goToAlternativeRoutes(HttpError.ERROR_503)
        504 -> goToAlternativeRoutes(HttpError.ERROR_504)
        else -> goToAlternativeRoutes(HttpError.OTHER)
    }
}
