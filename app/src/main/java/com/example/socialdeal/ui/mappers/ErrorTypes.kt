package com.example.socialdeal.ui.mappers

import com.example.socialdeal.data.values.ErrorTypes
import com.example.socialdeal.ui.values.ErrorMessageType

fun <T> ErrorTypes.convert(result: (ErrorMessageType) -> T) : T {
    return result(
        when (this) {
            ErrorTypes.FORBIDDEN,
            ErrorTypes.UNAUTHORISED,
            ErrorTypes.BAD_REQUEST,
            ErrorTypes.NOT_FOUND,
            ErrorTypes.UNKNOWN,
            ErrorTypes.JOB_CANCELLED,
            ErrorTypes.NETWORK_ERROR,
            ErrorTypes.NETWORK_TIMEOUT -> ErrorMessageType.UNKNOWN_ERROR
        }
    )
}