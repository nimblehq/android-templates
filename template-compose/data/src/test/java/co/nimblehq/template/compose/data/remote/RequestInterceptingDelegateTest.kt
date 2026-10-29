package co.nimblehq.template.compose.data.remote

import app.cash.turbine.test
import co.nimblehq.template.compose.data.remote.authenticator.RequestInterceptingDelegate
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Test

class RequestInterceptingDelegateTest {

    @Test
    fun `When requesting force logout, it emits a force logout event`() = runTest {
        RequestInterceptingDelegate.isForcedLogout.test {
            withContext(Dispatchers.Default) {
                RequestInterceptingDelegate.requestForceLogout()
            }

            awaitItem() shouldBe Unit
        }
    }

    @Test
    fun `When requesting force logout without any collector, it does not block`() = runTest {
        withContext(Dispatchers.Default) {
            RequestInterceptingDelegate.requestForceLogout()
        }
    }
}
