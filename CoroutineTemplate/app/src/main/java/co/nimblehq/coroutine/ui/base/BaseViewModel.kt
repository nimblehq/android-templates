package co.nimblehq.coroutine.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.nimblehq.coroutine.lib.IsLoading
import co.nimblehq.coroutine.util.DispatchersProvider
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@Suppress("PropertyName")
abstract class BaseViewModel(private val dispatchers: DispatchersProvider) : ViewModel() {

    private var loadingCount: Int = 0

    private val _showLoading = MutableStateFlow(false)
    val showLoading: StateFlow<IsLoading>
        get() = _showLoading

    protected val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String>
        get() = _error

    protected val _navigator = MutableSharedFlow<NavigationEvent>()
    val navigator: SharedFlow<NavigationEvent>
        get() = _navigator

    /**
     * To show loading manually, should call `hideLoading` after
     */
    protected fun showLoading() {
        if (loadingCount == 0) {
            _showLoading.value = true
        }
        loadingCount++
    }

    /**
     * To hide loading manually, should be called after `showLoading`
     */
    protected fun hideLoading() {
        loadingCount--
        if (loadingCount == 0) {
            _showLoading.value = false
        }
    }

    fun execute(dispatchersType: DispatchersType = DispatchersType.IO, job: suspend () -> Unit) =
        viewModelScope.launch(
            when (dispatchersType) {
                DispatchersType.IO -> dispatchers.io
                DispatchersType.MAIN -> dispatchers.main
                else -> dispatchers.default
            }
        ) {
            job.invoke()
        }
}

enum class DispatchersType {
    IO, MAIN, DEFAULT
}
