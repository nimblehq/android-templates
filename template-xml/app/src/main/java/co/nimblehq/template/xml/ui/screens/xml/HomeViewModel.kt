package co.nimblehq.template.xml.ui.screens.xml

import co.nimblehq.template.xml.domain.usecase.UseCase
import co.nimblehq.template.xml.model.UiModel
import co.nimblehq.template.xml.model.toUiModels
import co.nimblehq.template.xml.ui.base.BaseViewModel
import co.nimblehq.template.xml.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: UseCase,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    private val _uiModels = MutableStateFlow<List<UiModel>>(emptyList())
    val uiModels: StateFlow<List<UiModel>>
        get() = _uiModels

    init {
        execute {
            showLoading()
            useCase()
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
}
