package co.nimblehq.template.compose.ui.screens.home

import androidx.lifecycle.viewModelScope
import co.nimblehq.template.compose.domain.usecase.UseCase
import co.nimblehq.template.compose.model.UiModel
import co.nimblehq.template.compose.model.toUiModel
import co.nimblehq.template.compose.ui.base.BaseViewModel
import co.nimblehq.template.compose.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    dispatchersProvider: DispatchersProvider,
    useCase: UseCase,
) : BaseViewModel() {

    private val _uiModels = MutableStateFlow<List<UiModel>>(emptyList())
    val uiModels: StateFlow<List<UiModel>> = _uiModels

    init {
        useCase()
            .injectLoading()
            .onEach { result ->
                val uiModels = result.map { it.toUiModel() }
                _uiModels.emit(uiModels)
            }
            .flowOn(dispatchersProvider.io)
            .catch { e -> _error.emit(e) }
            .launchIn(viewModelScope)
    }
}
