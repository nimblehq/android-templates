package co.nimblehq.template.data.extensions

import co.nimblehq.template.data.response.ErrorResponse
import co.nimblehq.template.data.service.ApiException
import co.nimblehq.template.data.service.NoConnectivityException
import co.nimblehq.template.data.service.providers.MoshiBuilderProvider
import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.io.InterruptedIOException
import java.net.UnknownHostException
import kotlin.experimental.ExperimentalTypeInference

@Suppress("TooGenericExceptionCaught")
@OptIn(ExperimentalTypeInference::class)
fun <T> flowTransform(@BuilderInference block: suspend FlowCollector<T>.() -> T) = flow {
    val result = try {
        block()
    } catch (e: Exception) {
        throw e.mapError()
    }
    emit(result)
}

private fun Throwable.mapError(): Throwable {
    return when (this) {
        is UnknownHostException, is InterruptedIOException -> NoConnectivityException
        is HttpException -> {
            val errorResponse = parseErrorResponse(this)
            ApiException(errorResponse, response())
        }
        else -> this
    }
}

private fun parseErrorResponse(httpException: HttpException): ErrorResponse? {
    val jsonString = httpException.response()?.errorBody()?.string()
    return parseErrorResponse(jsonString)
}

private fun parseErrorResponse(jsonString: String?): ErrorResponse? {
    return try {
        val moshi = MoshiBuilderProvider.moshiBuilder.build()
        val adapter = moshi.adapter(ErrorResponse::class.java)
        adapter.fromJson(jsonString.orEmpty())
    } catch (exception: IOException) {
        null
    } catch (exception: JsonDataException) {
        null
    }
}
