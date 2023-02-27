package co.nimblehq.sample.xml.ui.screens.home

import co.nimblehq.sample.xml.domain.usecase.*
import co.nimblehq.sample.xml.model.UiModel
import co.nimblehq.sample.xml.model.toUiModels
import co.nimblehq.sample.xml.ui.base.BaseViewModel
import co.nimblehq.sample.xml.ui.base.NavigationEvent
import co.nimblehq.sample.xml.util.DispatchersProvider
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

    private val _isFirstTimeLaunch = MutableSharedFlow<Boolean>()
    val isFirstTimeLaunch: SharedFlow<Boolean>
        get() = _isFirstTimeLaunch

    init {
        execute {
            showLoading()
            getModelsUseCase()
                .catch {
                    _error.emit(it)
                }
                .collect { result ->
                    val uiModels = result.toUiModels()
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
        execute { _navigator.emit(NavigationEvent.Second(uiModel)) }
    }
}
