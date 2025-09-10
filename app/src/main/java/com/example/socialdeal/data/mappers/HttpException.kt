package com.example.socialdeal.data.mappers

import com.example.socialdeal.data.values.ErrorTypes
import retrofit2.HttpException

@JvmName("HttpExceptionToErrorTypes")
fun <R> HttpException.convert(result: (ErrorTypes) -> R) : R {
    return result(
        when (this.code()) {
            400 -> { ErrorTypes.BAD_REQUEST }
            401 -> { ErrorTypes.UNAUTHORISED }
            403 -> { ErrorTypes.FORBIDDEN }
            404 -> { ErrorTypes.NOT_FOUND }
            else -> ErrorTypes.UNKNOWN
        }
    )
}