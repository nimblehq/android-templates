package co.nimblehq.template.compose.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

/*
* Intent -> user intent (from UI to VM)
* Event -> one-off event (from VM to UI)
* View State -> Current State of the UI
* */
abstract class BaseViewModel<INTENT : BaseIntent, UI_MODEL>(
    initialState: BaseViewState<UI_MODEL> = BaseViewState.Initial(),
) : ViewModel() {

    private val tag = "BaseViewModel"

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<BaseViewState<UI_MODEL>> = _state.asStateFlow()

    val uiModel: UI_MODEL?
        get() = state.value.uiModel

    private val _intent = MutableSharedFlow<INTENT>()

    init {
        subscribeToIntents()
    }

    private fun subscribeToIntents() {
        viewModelScope.launch {
            _intent.collect {
                handleIntent(it)
            }
        }
    }

    fun setIntent(intent: INTENT) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    protected abstract fun handleIntent(intent: INTENT)

    private val _events = MutableSharedFlow<BaseEvent>()
    val events = _events.asSharedFlow()

    protected fun emitEvent(event: BaseEvent) {
        viewModelScope.launch {
            try {
                _events.emit(event)
            } catch (e: Exception) {
                Timber.tag(tag).e(e, "emitEvent error")
            }
        }
    }

    protected fun emitState(newState: BaseViewState<UI_MODEL>) {
        try {
            _state.value = newState
        } catch (e: Exception) {
            Timber.tag(tag).e(e, "emitState error")
        }
    }

    protected fun emitNavigation(destination: NavKey) {
        emitEvent(NavigationEvent(destination))
    }

    protected fun emitError(error: Throwable) {
        emitEvent(ErrorEvent(error))
    }
}
