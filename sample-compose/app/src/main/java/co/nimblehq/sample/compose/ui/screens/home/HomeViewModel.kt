package co.nimblehq.sample.compose.ui.screens.home

import androidx.lifecycle.viewModelScope
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

    private val _firstTimeLaunch = MutableStateFlow(false)

    val homeViewState: StateFlow<HomeViewState> = combine(
        _uiModels,
        _firstTimeLaunch,
        showLoading
    ) { uiModels, firstTimeLaunch, showLoading ->
        HomeViewState(
            uiModels = uiModels,
            firstTimeLaunch = firstTimeLaunch,
            showLoading = showLoading
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeViewState()
    )

    init {
        execute {
            showLoading()
            val getModelsFlow = getModelsUseCase()
                .catch {
                    _error.emit(it)
                }
            val getFirstTimeLaunchPreferencesFlow = getFirstTimeLaunchPreferencesUseCase()
                .catch {
                    _error.emit(it)
                }
            getModelsFlow.combine(getFirstTimeLaunchPreferencesFlow) { uiModels, firstTimeLaunch ->
                _uiModels.emit(uiModels.toUiModels())

                _firstTimeLaunch.emit(firstTimeLaunch)
                if (firstTimeLaunch) {
                    updateFirstTimeLaunchPreferencesUseCase(false)
                }
            }.collect{
                hideLoading()
            }
        }
    }

    fun navigateToSecond(uiModel: UiModel) {
        execute { _navigator.emit(AppDestination.Second.buildDestination(uiModel.id)) }
    }
}
