package co.nimblehq.sample.compose.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * The single immutable state a screen renders.
 */
interface ViewState

/**
 * A user action sent from the UI to the ViewModel.
 */
interface ViewIntent

/**
 * A one-off event (navigation, message) sent from the ViewModel to the UI.
 */
interface ViewEffect

abstract class BaseViewModel<VS : ViewState, VI : ViewIntent, VE : ViewEffect>(
    initialViewState: VS,
) : ViewModel() {

    private val _viewState = MutableStateFlow(initialViewState)
    val viewState: StateFlow<VS> = _viewState.asStateFlow()

    /**
     * [Channel] guarantees the delivery of effects emitted while the UI is not collecting yet,
     * e.g. effects sent from `init`.
     */
    private val _viewEffect = Channel<VE>(Channel.BUFFERED)
    val viewEffect: Flow<VE> = _viewEffect.receiveAsFlow()

    protected val currentViewState: VS
        get() = _viewState.value

    /**
     * The single entry point for all user intents from the UI.
     */
    abstract fun onIntent(intent: VI)

    /**
     * Updates the current [ViewState] into a new one.
     */
    protected fun updateViewState(updater: (VS) -> VS) {
        _viewState.update(updater)
    }

    protected fun sendViewEffect(effect: VE) {
        viewModelScope.launch { _viewEffect.send(effect) }
    }

    protected fun launch(context: CoroutineContext = EmptyCoroutineContext, job: suspend () -> Unit) =
        viewModelScope.launch(context) {
            job.invoke()
        }
}
