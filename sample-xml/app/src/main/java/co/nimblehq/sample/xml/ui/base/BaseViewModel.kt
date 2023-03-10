package co.nimblehq.sample.xml.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.nimblehq.sample.xml.lib.IsLoading
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@Suppress("PropertyName")
abstract class BaseViewModel : ViewModel() {

    private var loadingCount: Int = 0

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<IsLoading> = _isLoading

    protected val _error = MutableSharedFlow<Throwable>()
    val error: SharedFlow<Throwable> = _error

    protected val _navigator = MutableSharedFlow<NavigationEvent>()
    val navigator: SharedFlow<NavigationEvent> = _navigator

    /**
     * To show loading manually, should call `hideLoading` after
     */
    protected fun showLoading() {
        if (loadingCount == 0) {
            _isLoading.value = true
        }
        loadingCount++
    }

    /**
     * To hide loading manually, should be called after `showLoading`
     */
    protected fun hideLoading() {
        loadingCount--
        if (loadingCount == 0) {
            _isLoading.value = false
        }
    }

    protected fun launch(job: suspend () -> Unit) =
        viewModelScope.launch {
            job.invoke()
        }

    protected fun <T> Flow<T>.injectLoading(): Flow<T> = this
        .onStart { showLoading() }
        .onCompletion { hideLoading() }
}
