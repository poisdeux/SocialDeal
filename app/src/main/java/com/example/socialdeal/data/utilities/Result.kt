package com.example.socialdeal.data.utilities

sealed class Result<S, F> {
    data class Failure<S, F>(val error: F) : Result<S, F>()
    data class Success<S, F>(val result: S) : Result<S, F>()
}