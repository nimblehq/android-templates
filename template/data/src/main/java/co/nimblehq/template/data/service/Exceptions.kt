package co.nimblehq.template.data.service

import co.nimblehq.template.data.response.ErrorResponse
import retrofit2.HttpException
import retrofit2.Response

object NoConnectivityException : RuntimeException()

data class ApiException(
    val error: ErrorResponse?,
    val response: Response<*>?
) : HttpException(response)
