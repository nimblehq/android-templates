package co.nimblehq.template.compose.common

sealed class BaseViewState<T> {
    abstract val uiModel: T?

    data class Initial<T>(
        override val uiModel: T? = null
    ) : BaseViewState<T>()

    data class Loading<T>(
        override val uiModel: T? = null
    ) : BaseViewState<T>()

    data class Loaded<T>(
        override val uiModel: T
    ) : BaseViewState<T>()

    data class Error<T>(
        val error: Any,
        override val uiModel: T? = null
    ) : BaseViewState<T>()
}
