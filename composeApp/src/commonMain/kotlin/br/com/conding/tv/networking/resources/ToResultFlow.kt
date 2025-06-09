package br.com.conding.tv.networking.resources

import br.com.conding.tv.networking.resources.NetworkingUtils.ERROR_TIMEOUT
import br.com.conding.tv.networking.resources.NetworkingUtils.ERROR_UNKNOWN
import br.com.conding.tv.networking.resources.StatusCode.NUMBER_200
import br.com.conding.tv.networking.resources.StatusCode.NUMBER_299
import br.com.conding.tv.networking.resources.StatusCode.NUMBER_400
import br.com.conding.tv.networking.resources.StatusCode.NUMBER_499
import br.com.conding.tv.networking.resources.StatusCode.NUMBER_500
import br.com.conding.tv.networking.resources.StatusCode.NUMBER_599
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal inline fun <reified T> toResultFlow(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    crossinline call: suspend () -> HttpResponse
): Flow<ObserveNetworkStateHandler<T>> = flow {
    emit(ObserveNetworkStateHandler.Loading(l = true))
    try {
        val response = call()
        when (response.status.value) {
            in NUMBER_200..NUMBER_299 -> {
                val data = response.body<T>()
                emit(ObserveNetworkStateHandler.Success(s = data))
            }

            in NUMBER_400..NUMBER_499 -> {
                val errorResponse = response.body<ResponseError>()
                emit(
                    ObserveNetworkStateHandler.Error(
                        e = DescriptionError(
                            code = errorResponse.status,
                            type = ErrorType.CLIENT,
                            message = errorResponse.message
                        )
                    )
                )
            }

            in NUMBER_500..NUMBER_599 -> {
                emit(
                    ObserveNetworkStateHandler.Error(
                        e = DescriptionError(
                            code = response.status.value,
                            type = ErrorType.SERVER,
                            message = response.status.description
                        )
                    )
                )
            }

            else -> {
                emit(
                    ObserveNetworkStateHandler.Error(
                        e = DescriptionError(
                            type = ErrorType.INTERNAL,
                            message = ERROR_UNKNOWN
                        )
                    )
                )
            }
        }
    } catch (e: Exception) {
        emit(
            ObserveNetworkStateHandler.Error(
                e = DescriptionError(
                    type = ErrorType.INTERNAL,
                    message = e.message ?: ""
                )
            )
        )
    } catch (e: SocketTimeoutException) {
        emit(
            ObserveNetworkStateHandler.Error(
                e = DescriptionError(
                    type = ErrorType.CLIENT,
                    message = ERROR_TIMEOUT
                )
            )
        )
    }

}.catch {
    emit(
        ObserveNetworkStateHandler.Error(
            e = DescriptionError(
                type = ErrorType.INTERNAL,
                message = it.message ?: ""
            )
        )
    )
}.flowOn(dispatcher)
