package co.nimblehq.sample.compose.ui.screens.home

import android.Manifest
import co.nimblehq.sample.compose.domain.usecase.UseCase
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.model.toUiModels
import co.nimblehq.sample.compose.ui.base.BaseViewModel
import co.nimblehq.sample.compose.ui.base.NavigationEvent
import co.nimblehq.sample.compose.util.DispatchersProvider
import com.markodevcic.peko.PermissionResult
import com.markodevcic.peko.PermissionResult.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

// TODO: Rename to 'HomeViewModel'
@HiltViewModel
class HomeComposeViewModel @Inject constructor(
    private val useCase: UseCase,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    private val _uiModels = MutableStateFlow<List<UiModel>>(emptyList())
    val uiModels: StateFlow<List<UiModel>>
        get() = _uiModels

    private val _requestPermissions = MutableStateFlow<Array<out String>>(emptyArray())
    val requestPermissions: StateFlow<Array<out String>>
        get() = _requestPermissions

    init {
        execute {
            showLoading()
            useCase()
                .catch {
                    _error.emit(it)
                }
                .collect { result ->
                    val uiModels = result.toUiModels()
                    _uiModels.emit(uiModels)
                }
            hideLoading()

            requestPermissions()
        }
    }

    fun navigateToSecond(uiModel: UiModel) {
        execute { _navigator.emit(NavigationEvent.Second(uiModel)) }
    }

    private suspend fun requestPermissions() {
        _requestPermissions.emit(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA
            )
        )
    }

    fun onPermissionResult(permissionResult: PermissionResult) {
        when (permissionResult) {
            is Granted -> Timber.d("${permissionResult.grantedPermissions} granted")
            is Denied.JustDenied -> Timber.d("${permissionResult.deniedPermissions} denied")
            is Denied.NeedsRationale -> Timber.d("${permissionResult.deniedPermissions} needs rationale")
            is Denied.DeniedPermanently -> Timber.d("${permissionResult.deniedPermissions} denied for good")
            is Cancelled -> Timber.d("request cancelled")
        }
    }
}
