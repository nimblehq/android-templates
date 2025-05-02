package co.nimblehq.template.compose.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Suppress("PropertyName")
abstract class BaseViewModel : ViewModel() {

    private var loadingCount: Int = 0

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    protected val _error = MutableSharedFlow<Throwable>()
    val error = _error.asSharedFlow()

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

    protected fun launch(context: CoroutineContext = EmptyCoroutineContext, job: suspend () -> Unit) =
        viewModelScope.launch(context) {
            job.invoke()
        }

    protected fun <T> Flow<T>.injectLoading(): Flow<T> = this
        .onStart { showLoading() }
        .onCompletion { hideLoading() }
}
