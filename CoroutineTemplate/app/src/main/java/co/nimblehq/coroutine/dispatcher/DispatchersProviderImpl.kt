package co.nimblehq.coroutine.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

class DispatchersProviderImpl : DispatchersProvider {

    override fun getIO(): CoroutineDispatcher = Dispatchers.IO

    override fun getMain(): MainCoroutineDispatcher = Dispatchers.Main

    override fun getDefault(): CoroutineDispatcher = Dispatchers.Default
}
