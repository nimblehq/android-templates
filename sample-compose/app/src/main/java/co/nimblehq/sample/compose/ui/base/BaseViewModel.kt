package co.nimblehq.sample.compose.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.nimblehq.sample.compose.lib.IsLoading
import co.nimblehq.sample.compose.ui.AppDestination
import co.nimblehq.sample.compose.util.DispatchersProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@Suppress("PropertyName")
abstract class BaseViewModel(private val dispatchersProvider: DispatchersProvider) : ViewModel() {

    private var loadingCount: Int = 0

    private val _isShowLoading = MutableStateFlow(false)
    val isShowLoading: StateFlow<IsLoading>
        get() = _isShowLoading

    protected val _error = MutableSharedFlow<Throwable>()
    val error: SharedFlow<Throwable>
        get() = _error

    protected val _navigator = MutableSharedFlow<AppDestination>()
    val navigator: SharedFlow<AppDestination>
        get() = _navigator

    /**
     * To show loading manually, should call `hideLoading` after
     */
    protected fun showLoading() {
        if (loadingCount == 0) {
            _isShowLoading.value = true
        }
        loadingCount++
    }

    /**
     * To hide loading manually, should be called after `showLoading`
     */
    protected fun hideLoading() {
        loadingCount--
        if (loadingCount == 0) {
            _isShowLoading.value = false
        }
    }

    fun execute(coroutineDispatcher: CoroutineDispatcher = dispatchersProvider.io, job: suspend () -> Unit) =
        viewModelScope.launch(coroutineDispatcher) {
            job.invoke()
        }
}
