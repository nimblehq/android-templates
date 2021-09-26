package co.nimblehq.coroutine.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

class DispatchersProviderImpl : DispatchersProvider {

    override fun getMain(): MainCoroutineDispatcher = Dispatchers.Main

    override fun getIO(): CoroutineDispatcher = Dispatchers.IO

    override fun getDefault(): CoroutineDispatcher = Dispatchers.Default
}
