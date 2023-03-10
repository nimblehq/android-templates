package co.nimblehq.sample.compose.ui.screens.home

import androidx.lifecycle.viewModelScope
import co.nimblehq.sample.compose.domain.usecase.*
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.model.toUiModel
import co.nimblehq.sample.compose.ui.AppDestination
import co.nimblehq.sample.compose.ui.base.BaseViewModel
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
    val uiModels: StateFlow<List<UiModel>> = _uiModels

    private val _isFirstTimeLaunch = MutableStateFlow(false)
    val isFirstTimeLaunch: StateFlow<Boolean> = _isFirstTimeLaunch

    init {
        getModelsUseCase()
            .injectLoading()
            .onEach { result ->
                val uiModels = result.map { it.toUiModel() }
                _uiModels.emit(uiModels)
            }
            .catch { e -> _error.emit(e) }
            .launchIn(viewModelScope)

        launch {
            val isFirstTimeLaunch = isFirstTimeLaunchPreferencesUseCase()
                .catch { e -> _error.emit(e) }
                .first()

            _isFirstTimeLaunch.emit(isFirstTimeLaunch)
            if (isFirstTimeLaunch) {
                updateFirstTimeLaunchPreferencesUseCase(false)
            }
        }
    }

    fun navigateToSecond(uiModel: UiModel) {
        launch { _navigator.emit(AppDestination.Second.createRoute(uiModel.id)) }
    }

    fun navigateToThird(uiModel: UiModel) {
        launch { _navigator.emit(AppDestination.Third.addParcel(uiModel)) }
    }
}
