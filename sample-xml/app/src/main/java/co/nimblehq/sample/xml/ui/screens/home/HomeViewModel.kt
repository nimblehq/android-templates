package co.nimblehq.sample.xml.ui.screens.home

import co.nimblehq.sample.xml.domain.usecase.GetModelsUseCase
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
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    private val _uiModels = MutableStateFlow<List<UiModel>>(emptyList())
    val uiModels: StateFlow<List<UiModel>>
        get() = _uiModels

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
    }

    fun navigateToSecond(uiModel: UiModel) {
        execute { _navigator.emit(NavigationEvent.Second(uiModel)) }
    }
}
