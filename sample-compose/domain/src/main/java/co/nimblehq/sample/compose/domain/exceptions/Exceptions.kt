package co.nimblehq.sample.compose.domain.exceptions

import co.nimblehq.sample.compose.domain.models.Error

object NoConnectivityException : RuntimeException()

data class ApiException(
    val error: Error?,
    val httpCode: Int,
    val httpMessage: String?
) : RuntimeException()
