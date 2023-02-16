package co.nimblehq.sample.compose.ui.screens.home

import co.nimblehq.sample.compose.domain.usecase.*
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.model.toUiModel
import co.nimblehq.sample.compose.ui.AppDestination
import co.nimblehq.sample.compose.ui.base.BaseViewModel
import co.nimblehq.sample.compose.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getModelsUseCase: GetModelsUseCase,
    private val isFirstTimeLaunchPreferencesUseCase: IsFirstTimeLaunchPreferencesUseCase,
    private val updateFirstTimeLaunchPreferencesUseCase: UpdateFirstTimeLaunchPreferencesUseCase,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    private val _uiModels = MutableStateFlow<List<UiModel>>(emptyList())
    val uiModels: StateFlow<List<UiModel>>
        get() = _uiModels

    private val _isFirstTimeLaunch = MutableStateFlow(false)
    val isFirstTimeLaunch: StateFlow<Boolean>
        get() = _isFirstTimeLaunch

    init {
        execute {
            showLoading()
            getModelsUseCase()
                .catch {
                    _error.emit(it)
                }
                .collect { result ->
                    val uiModels = result.map { it.toUiModel() }
                    _uiModels.emit(uiModels)
                }
            hideLoading()
        }

        execute {
            val isFirstTimeLaunch = isFirstTimeLaunchPreferencesUseCase()
                .catch {
                    _error.emit(it)
                }.first()

            _isFirstTimeLaunch.emit(isFirstTimeLaunch)
            if (isFirstTimeLaunch) {
                updateFirstTimeLaunchPreferencesUseCase(false)
            }
        }
    }

    fun navigateToSecond(uiModel: UiModel) {
        execute { _navigator.emit(AppDestination.Second.createRoute(uiModel.id)) }
    }

    fun navigateToThird(uiModel: UiModel) {
        execute { _navigator.emit(AppDestination.Third.addParcel(uiModel)) }
    }
}
