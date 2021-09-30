package co.nimblehq.coroutine.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.nimblehq.coroutine.lib.IsLoading
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    fun execute(job: suspend () -> Unit, dispatchers: CoroutineDispatcher = Dispatchers.IO) =
        viewModelScope.launch {
            withContext(dispatchers) { job.invoke() }
        }
}
