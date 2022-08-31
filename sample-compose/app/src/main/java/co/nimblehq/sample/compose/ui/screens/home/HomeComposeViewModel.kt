package co.nimblehq.sample.compose.ui.screens.home

import co.nimblehq.sample.compose.domain.usecase.UseCase
import co.nimblehq.sample.compose.domain.usecase.UseCaseResult
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.model.toUiModels
import co.nimblehq.sample.compose.ui.base.BaseViewModel
import co.nimblehq.sample.compose.ui.base.NavigationEvent
import co.nimblehq.sample.compose.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    init {
        execute {
            showLoading()
            when (val result = useCase.execute()) {
                is UseCaseResult.Success -> {
                    val uiModels = result.data.toUiModels()
                    _uiModels.emit(uiModels)
                }
                is UseCaseResult.Error -> {
                    val errorMessage = result.exception.message.orEmpty()
                    _error.emit(errorMessage)
                }
            }
            hideLoading()
        }
    }

    fun navigateToSecond(uiModel: UiModel) {
        execute { _navigator.emit(NavigationEvent.Second(uiModel)) }
    }
}
