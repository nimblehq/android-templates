package co.nimblehq.sample.compose.ui.screens.details

import androidx.lifecycle.viewModelScope
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

@HiltViewModel(assistedFactory = DetailsViewModel.Factory::class)
class DetailsViewModel @AssistedInject constructor(
    @Assisted private val key: DetailsScreen,
    private val getDetailsUseCase: GetDetailsUseCase,
    private val searchUserUseCase: SearchUserUseCase,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel() {

    private val _uiModel = MutableStateFlow<UiModel?>(null)
    val uiModel = _uiModel.asStateFlow()

    private val _isLiked = MutableStateFlow(false)
    val isLiked = _isLiked.asStateFlow()

    private val _isFromDeepLink = MutableStateFlow(false)
    val isFromDeepLink = _isFromDeepLink.asStateFlow()

    private val _username = MutableStateFlow<String?>(null)
    val username = _username.asStateFlow()

    private val _onLoginRequired = MutableSharedFlow<Unit>()
    val onLoginRequired = _onLoginRequired.asSharedFlow()

    init {
        when (key) {
            is DetailsScreen.Details -> loadDetails(key.id)
            is DetailsScreen.Search -> searchUser(key.username)
        }
    }

    private fun loadDetails(id: Int) {
        getDetailsUseCase(id)
            .injectLoading()
            .onEach { model ->
                _uiModel.emit(model.toUiModel())
                _isFromDeepLink.emit(false)
            }
            .flowOn(dispatchersProvider.io)
            .catch { e -> _error.emit(e) }
            .launchIn(viewModelScope)
    }

    private fun searchUser(username: String) {
        searchUserUseCase(username)
            .injectLoading()
            .onEach { models ->
                _uiModel.emit(models.firstOrNull()?.toUiModel())
                _isFromDeepLink.emit(true)
            }
            .flowOn(dispatchersProvider.io)
            .catch { e -> _error.emit(e) }
            .launchIn(viewModelScope)
    }

    fun onClickLike() {
        launch {
            if (!_isLiked.value) {
                _onLoginRequired.emit(Unit)
            } else {
                _isLiked.emit(false)
            }
        }
    }

    fun changeUsername(username: String) {
        launch {
            _username.emit(username)
            _isLiked.emit(true)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(key: DetailsScreen): DetailsViewModel
    }
}
