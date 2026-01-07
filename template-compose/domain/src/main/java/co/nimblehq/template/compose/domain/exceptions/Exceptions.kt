package co.nimblehq.template.compose.domain.exceptions

import co.nimblehq.template.compose.domain.models.Error

object NoConnectivityException : RuntimeException()

data class ApiException(
    val error: Error?,
    val httpCode: Int,
    val httpMessage: String?,
) : RuntimeException()
