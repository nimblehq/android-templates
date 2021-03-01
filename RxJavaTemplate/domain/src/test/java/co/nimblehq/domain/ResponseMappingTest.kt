package co.nimblehq.domain

import co.nimblehq.data.service.error.JsonApiException
import co.nimblehq.data.service.error.NoConnectivityException
import co.nimblehq.data.service.error.UnknownException
import io.reactivex.Single
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Response
import java.io.InterruptedIOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ResponseMappingTest {

    @Test
    fun `should transform successful api response`() {
        val source = Single.just(Response.success("result"))

        val result = source.transform().test()

        result.assertValue { it == "result" }
    }

    @Test
    fun `should transform SocketTimeoutException to SocketTimeoutException error`() {
        val source = Single.error<Response<String>>(SocketTimeoutException("timeout"))

        val result = source.transform().test()

        result.assertError {
            it is SocketTimeoutException
        }
    }

    @Test
    fun `should transform ConnectException to NoConnectivityException error`() {
        val source = Single.error<Response<String>>(UnknownHostException("someReason"))

        val result = source.transform().test()

        result.assertError {
            it is NoConnectivityException
        }
    }

    @Test
    fun `should transform InterruptedIOException to NoConnectivityException error`() {
        val source = Single.error<Response<String>>(InterruptedIOException("someReason"))

        val result = source.transform().test()

        result.assertError {
            it is NoConnectivityException
        }
    }

    @Test
    fun `should transform error response from api to JsonApiException error`() {
        val source = Single.just(
            Response.error<Any>(
                404, ResponseBody.create(
                null, "{\n" +
                "  \"code\": \"unauthorized\",\n" +
                "  \"details\": {},\n" +
                "  \"message\": \"Unauthorized\",\n" +
                "  \"object\": \"error\"\n" +
                "}"
            )
            )
        )

        val result = source.transform().test()

        result
            .assertError {
                it is JsonApiException
            }.assertError {
                (it as? JsonApiException)?.response?.code() == 404
            }
    }

    @Test
    fun `should transform error response from api to UnknownException error in case of error passing error`() {
        val source = Single.just(
            Response.error<Any>(
                404, ResponseBody.create(
                null, "{\n" +
                "  \"code\": \"unauthorized\",\n" +
                "}"
            )
            )
        )

        val result = source.transform().test()

        result.assertError {
            it is UnknownException
        }
    }

    @Test
    fun `should transform error response from api to UnknownException error in case of Body is null`() {
        val source = Single.just(Response.success(null))

        val result = source.transform().test()

        result.assertError {
            it is UnknownException
        }
    }

    @Test
    fun `should transform error response from api to UnknownException error in case of ErrorBody is null`() {
        val source = Single.just(Response.success(null))

        val result = source.transform().test()

        result.assertError {
            it is UnknownException
        }
    }
}
