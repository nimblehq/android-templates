package co.nimblehq.sample.compose.test

import co.nimblehq.sample.compose.util.DispatchersProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
object TestDispatchersProvider : DispatchersProvider {

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    override val io: CoroutineDispatcher
        get() = testDispatcher

    override val main: CoroutineDispatcher
        get() = testDispatcher

    override val default: CoroutineDispatcher
        get() = testDispatcher
}
