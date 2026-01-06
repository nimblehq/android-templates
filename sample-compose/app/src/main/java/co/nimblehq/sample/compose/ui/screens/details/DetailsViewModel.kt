package co.nimblehq.sample.compose.ui.screens.details

import androidx.lifecycle.viewModelScope
import co.nimblehq.common.extensions.isNotNullOrBlank
import co.nimblehq.sample.compose.domain.usecases.GetDetailsUseCase
import co.nimblehq.sample.compose.domain.usecases.SearchUserUseCase
import co.nimblehq.sample.compose.ui.base.BaseViewModel
import co.nimblehq.sample.compose.ui.models.UiModel
import co.nimblehq.sample.compose.ui.models.toUiModel
import co.nimblehq.sample.compose.util.DispatchersProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailsViewModel.Factory::class)
class DetailsViewModel @AssistedInject constructor(
    @Assisted val details: DetailsScreen,
    getDetailsUseCase: GetDetailsUseCase,
    searchUserUseCase: SearchUserUseCase,
    dispatchersProvider: DispatchersProvider,
) : BaseViewModel() {

    private val _uiModel = MutableStateFlow<UiModel?>(null)
    val uiModel = _uiModel.asStateFlow()

    private val _isLiked = MutableStateFlow(false)
    val isLiked = _isLiked.asStateFlow()

    private val _onLoginRequired = MutableSharedFlow<Unit>()
    val onLoginRequired = _onLoginRequired.asSharedFlow()

    private val _username = MutableStateFlow<String?>(null)
    val username = _username.asStateFlow()

    private val _isFromDeepLink = MutableStateFlow(false)
    val isFromDeepLink = _isFromDeepLink.asStateFlow()

    @AssistedFactory
    interface Factory {
        fun create(details: DetailsScreen): DetailsViewModel
    }

    init {
        if (details is DetailsScreen.Details) {
            getDetailsUseCase(details.id)
                .injectLoading()
                .onEach { result ->
                    _uiModel.emit(result.toUiModel())
                }
                .flowOn(dispatchersProvider.io)
                .catch { e -> _error.emit(e) }
                .launchIn(viewModelScope)
        } else if (details is DetailsScreen.Search) {
            _isFromDeepLink.value = true
            searchUserUseCase(details.username)
                .injectLoading()
                .onEach { result ->
                    result.firstOrNull()?.toUiModel()?.let {
                        _uiModel.emit(it)
                    }
                }
                .flowOn(dispatchersProvider.io)
                .catch { e -> _error.emit(e) }
                .launchIn(viewModelScope)
        }
    }

    fun onClickLike() {
        if (_username.value.isNotNullOrBlank()) {
            _isLiked.value = !_isLiked.value
        } else {
            viewModelScope.launch {
                _onLoginRequired.emit(Unit)
            }
        }
    }

    fun changeUsername(username: String) {
        _username.value = username
    }
}
