package co.nimblehq.coroutine.domain.usecase

sealed class ChatUseCaseResult<out T : Any?> {
    class Success<out T : Any>(val data: T) : ChatUseCaseResult<T>()
    class Error(val exception: Throwable) : ChatUseCaseResult<Nothing>()
}
