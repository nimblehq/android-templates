package co.nimblehq.template.xml.ui.screens.home

import androidx.lifecycle.viewModelScope
import co.nimblehq.template.xml.domain.usecase.UseCase
import co.nimblehq.template.xml.model.UiModel
import co.nimblehq.template.xml.model.toUiModel
import co.nimblehq.template.xml.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    useCase: UseCase
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
            .catch { e -> _error.emit(e) }
            .launchIn(viewModelScope)
    }
}
