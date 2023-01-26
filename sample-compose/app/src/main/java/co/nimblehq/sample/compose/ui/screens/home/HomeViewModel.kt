package co.nimblehq.sample.compose.ui.screens.home

import co.nimblehq.sample.compose.domain.usecase.*
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.model.toUiModels
import co.nimblehq.sample.compose.ui.AppDestination
import co.nimblehq.sample.compose.ui.base.BaseViewModel
import co.nimblehq.sample.compose.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getModelsUseCase: GetModelsUseCase,
    private val getFirstTimeLaunchPreferencesUseCase: GetFirstTimeLaunchPreferencesUseCase,
    private val updateFirstTimeLaunchPreferencesUseCase: UpdateFirstTimeLaunchPreferencesUseCase,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    private val _uiModels = MutableStateFlow<List<UiModel>>(emptyList())
    val uiModels: StateFlow<List<UiModel>>
        get() = _uiModels

    private val _firstTimeLaunch = MutableStateFlow(false)
    val firstTimeLaunch: StateFlow<Boolean>
        get() = _firstTimeLaunch

    init {
        execute {
            val getModelsFlow = getModelsUseCase()
            val getFirstTimeLaunchPreferencesFlow = getFirstTimeLaunchPreferencesUseCase()

            getModelsFlow.combine(getFirstTimeLaunchPreferencesFlow) { uiModels, firstTimeLaunch ->
                _uiModels.emit(uiModels.toUiModels())

                _firstTimeLaunch.emit(firstTimeLaunch)
                if (firstTimeLaunch) {
                    updateFirstTimeLaunchPreferencesUseCase(false)
                }
            }.onStart {
                showLoading()
            }.onCompletion {
                hideLoading()
            }.catch {
                _error.emit(it)
            }.collect()
        }
    }

    fun navigateToSecond(uiModel: UiModel) {
        execute { _navigator.emit(AppDestination.Second.buildDestination(uiModel.id)) }
    }
}
