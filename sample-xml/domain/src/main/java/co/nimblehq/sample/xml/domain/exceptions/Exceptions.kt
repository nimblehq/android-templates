package co.nimblehq.sample.xml.domain.exceptions

import co.nimblehq.sample.xml.domain.model.Error

object NoConnectivityException : RuntimeException()

data class ApiException(
    val error: Error?,
    val httpCode: Int,
    val httpMessage: String?
) : RuntimeException()
