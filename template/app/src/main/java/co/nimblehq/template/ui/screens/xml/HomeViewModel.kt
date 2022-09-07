package co.nimblehq.template.ui.screens.xml

import co.nimblehq.template.domain.usecase.UseCase
import co.nimblehq.template.model.UiModel
import co.nimblehq.template.model.toUiModels
import co.nimblehq.template.ui.base.BaseViewModel
import co.nimblehq.template.util.DispatchersProvider
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
            useCase.execute()
                .catch {
                    val errorMessage = it.message.orEmpty()
                    _error.emit(errorMessage)
                }
                .collect { result ->
                    val uiModels = result.toUiModels()
                    _uiModels.emit(uiModels)
                }
            hideLoading()
        }
    }
}
