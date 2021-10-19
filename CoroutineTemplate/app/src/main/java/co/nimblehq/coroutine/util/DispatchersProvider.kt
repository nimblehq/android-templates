package co.nimblehq.coroutine.util

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProvider {
    fun getIO(): CoroutineDispatcher
    fun getMain(): CoroutineDispatcher
    fun getDefault(): CoroutineDispatcher
}
