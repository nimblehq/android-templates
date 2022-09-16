package co.nimblehq.template.domain.exceptions

import co.nimblehq.template.domain.model.Error

object NoConnectivityException : RuntimeException()

data class ApiException(
    val error: Error?,
    val httpCode: Int,
    val httpMessage: String?
) : RuntimeException()
