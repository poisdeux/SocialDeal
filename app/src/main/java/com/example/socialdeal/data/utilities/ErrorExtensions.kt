package com.example.socialdeal.data.utilities

import com.example.socialdeal.data.mappers.convert
import com.example.socialdeal.data.values.ErrorTypes
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import kotlin.coroutines.cancellation.CancellationException

suspend fun <S> tryWithErrorHandling(block: suspend () -> S): Result<S, ErrorTypes> {
    return try {
        Result.Success(block())
    } catch (_: SocketTimeoutException) {
        Result.Failure(ErrorTypes.NETWORK_TIMEOUT)
    } catch (ioException: IOException) {
        Timber.e(ioException)
        Result.Failure(ErrorTypes.NETWORK_ERROR)
    } catch (httpException: HttpException) {
        Timber.e(httpException)
        Result.Failure(httpException.convert { it } )
    } catch (_: CancellationException) {
        Result.Failure(ErrorTypes.JOB_CANCELLED)
    } catch (exceptionToHandle: Exception) {
        Timber.e(exceptionToHandle)
        Result.Failure(ErrorTypes.UNKNOWN)
    }
}