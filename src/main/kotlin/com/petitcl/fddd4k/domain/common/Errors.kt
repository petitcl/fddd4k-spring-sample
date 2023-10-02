package com.petitcl.fddd4k.domain.common

import arrow.core.Either


interface ApplicationError {
    val message: String
    val cause: Throwable?
}
abstract class DefaultError(
    override val message: String,
    override val cause: Throwable? = null,
) : ApplicationError

data class GenericError(
    override val message: String,
    override val cause: Throwable? = null,
) : DefaultError(message, cause) {
    constructor(cause: Throwable) : this(cause.message ?: "Unknown error", cause)
}

typealias ErrorOr<T> = Either<ApplicationError, T>

// bridge the gap between functional and imperative worlds
data class ApplicationException(
    val error: ApplicationError,
) : RuntimeException(error.message, error.cause)

fun <T> Either<ApplicationError, T>.getOrThrow(): T = when (this) {
    is Either.Left -> throw ApplicationException(this.value)
    is Either.Right -> this.value
}
