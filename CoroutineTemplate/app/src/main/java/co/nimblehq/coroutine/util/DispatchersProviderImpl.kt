package co.nimblehq.coroutine.util

import kotlinx.coroutines.*

class DispatchersProviderImpl : DispatchersProvider {

    override fun getIO(): CoroutineDispatcher = Dispatchers.IO

    override fun getMain(): MainCoroutineDispatcher = Dispatchers.Main

    override fun getDefault(): CoroutineDispatcher = Dispatchers.Default
}
