package co.nimblehq.sample.compose.ui.screens.details

import androidx.lifecycle.viewModelScope
import co.nimblehq.sample.compose.domain.usecases.GetDetailsUseCase
import co.nimblehq.sample.compose.ui.base.BaseViewModel
import co.nimblehq.sample.compose.ui.models.UiModel
import co.nimblehq.sample.compose.ui.models.toUiModel
import co.nimblehq.sample.compose.util.DispatchersProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel(assistedFactory = DetailsViewModel.Factory::class)
class DetailsViewModel @AssistedInject constructor(
    @Assisted val details: DetailsScreen,
    getDetailsUseCase: GetDetailsUseCase,
    dispatchersProvider: DispatchersProvider,
) : BaseViewModel() {

    private val _uiModel = MutableStateFlow<UiModel?>(null)
    val uiModel = _uiModel.asStateFlow()

    @AssistedFactory
    interface Factory {
        fun create(details: DetailsScreen): DetailsViewModel
    }

    init {
        getDetailsUseCase(details.id)
            .injectLoading()
            .onEach { result ->
                _uiModel.emit(result.toUiModel())
            }
            .flowOn(dispatchersProvider.io)
            .catch { e -> _error.emit(e) }
            .launchIn(viewModelScope)
    }
}
