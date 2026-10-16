package co.nimblehq.template.compose.data.remote.authenticator

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.runBlocking

object RequestInterceptingDelegate {

    private val _isForcedLogout: MutableSharedFlow<Unit> = MutableSharedFlow()
    val isForcedLogout = _isForcedLogout.asSharedFlow()

    @Synchronized
    internal fun requestForceLogout() {
        runBlocking {
            _isForcedLogout.emit(Unit)
        }
    }
}
