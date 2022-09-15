package co.nimblehq.template.data.extensions

import co.nimblehq.template.data.service.ApiException
import co.nimblehq.template.data.service.NoConnectivityException
import co.nimblehq.template.data.test.MockUtil
import co.nimblehq.template.domain.model.Model
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.io.IOException
import java.io.InterruptedIOException
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class ResponseMappingTest {

    @Test
    fun `When mapping API request flow failed with UnknownHostException, it returns mapped NoConnectivityException error`() =
        runBlockingTest {
            flowTransform<Model> {
                throw UnknownHostException()
            }.catch {
                it shouldBe NoConnectivityException
            }.collect()
        }

    @Test
    fun `When mapping API request flow failed with InterruptedIOException, it returns mapped NoConnectivityException error`() =
        runBlockingTest {
            flowTransform<Model> {
                throw InterruptedIOException()
            }.catch {
                it shouldBe NoConnectivityException
            }.collect()
        }

    @Test
    fun `When mapping API request flow failed with HttpException, it returns mapped ApiException error`() =
        runBlockingTest {
            val exception = MockUtil.mockHttpException
            flowTransform<Model> {
                throw exception
            }.catch {
                it shouldBe ApiException(MockUtil.errorResponse, exception.response())
            }.collect()
        }

    @Test
    fun `When mapping API request flow failed with unhandled exceptions, it should throw that error`() =
        runBlockingTest {
            val exception = IOException("Canceled")
            flowTransform<Model> {
                throw exception
            }.catch {
                it shouldBe exception
            }.collect()
        }
}
