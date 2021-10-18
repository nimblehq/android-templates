package co.nimblehq.coroutine.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.nimblehq.coroutine.lib.IsLoading
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@Suppress("PropertyName")
abstract class BaseViewModel : ViewModel() {

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

    fun execute(dispatchers: CoroutineDispatcher = Dispatchers.IO, job: suspend () -> Unit) =
        viewModelScope.launch (dispatchers) {
            job.invoke()
        }
}
