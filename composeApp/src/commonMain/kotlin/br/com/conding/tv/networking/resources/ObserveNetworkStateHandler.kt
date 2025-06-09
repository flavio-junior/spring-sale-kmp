package br.com.conding.tv.networking.resources

internal sealed class ObserveNetworkStateHandler<T>(
    val status: NetworkStatus,
    val result: T? = null,
    val exception: DescriptionError = DescriptionError(
        code = 0,
        type = ErrorType.CLIENT,
        message = ""
    )
) {
    data class Loading<T>(val l: Boolean) :
        ObserveNetworkStateHandler<T>(status = NetworkStatus.LOADING)

    data class Error<T>(
        val e: DescriptionError = DescriptionError(
            code = 0,
            type = ErrorType.CLIENT,
            message = ""
        )
    ) :
        ObserveNetworkStateHandler<T>(
            status = NetworkStatus.ERROR,
            exception = e
        )

    data class Success<T>(val s: T?) :
        ObserveNetworkStateHandler<T>(status = NetworkStatus.SUCCESS, result = s)
}
