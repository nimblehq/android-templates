package co.nimblehq.template.compose.ui.screens.list

import androidx.lifecycle.viewModelScope
import co.nimblehq.template.compose.common.BaseViewModel
import co.nimblehq.template.compose.common.BaseViewState
import co.nimblehq.template.compose.domain.usecases.UseCase
import co.nimblehq.template.compose.navigation.navigator.Up
import co.nimblehq.template.compose.ui.screens.list.model.ListUiModel
import co.nimblehq.template.compose.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val useCase: UseCase,
) : BaseViewModel<ListIntent, ListUiModel>() {

    init {
        loadModels()
    }

    override fun handleIntent(intent: ListIntent) {
        when (intent) {
            is ListIntent.LoadModels -> loadModels()
            is ListIntent.NavigateBack -> emitNavigation(Up)
        }
    }

    private fun loadModels() {
        useCase()
            .onStart { emitState(BaseViewState.Loading(uiModel = uiModel)) }
            .onEach { models ->
                emitState(BaseViewState.Loaded(uiModel = ListUiModel(ids = models.mapNotNull { it.id })))
            }
            .flowOn(dispatchersProvider.io)
            .catch { e ->
                emitState(BaseViewState.Error(error = e, uiModel = uiModel))
                emitError(e)
            }
            .launchIn(viewModelScope)
    }
}
