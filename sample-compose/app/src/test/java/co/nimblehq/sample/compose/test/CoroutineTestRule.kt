package co.nimblehq.sample.compose.test

import co.nimblehq.sample.compose.util.DispatchersProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineTestRule(
    var testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {

    val testDispatcherProvider = object : DispatchersProvider {

        override val io: CoroutineDispatcher
            get() = testDispatcher

        override val main: CoroutineDispatcher
            get() = testDispatcher

        override val default: CoroutineDispatcher
            get() = testDispatcher
    }

    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
