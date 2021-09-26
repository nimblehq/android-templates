package co.nimblehq.coroutine.ui.screens.home

import androidx.lifecycle.viewModelScope
import co.nimblehq.coroutine.dispatcher.DispatchersProvider
import co.nimblehq.coroutine.ui.base.BaseViewModel
import co.nimblehq.coroutine.ui.base.NavigationEvent
import co.nimblehq.coroutine.ui.screens.second.SecondBundle
import co.nimblehq.domain.entity.UserEntity
import co.nimblehq.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface Output {

    val users: StateFlow<List<UserEntity>>

    fun navigateToSecond(bundle: SecondBundle)

    fun navigateToCompose()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers), Output {

    private val _users = MutableStateFlow<List<UserEntity>>(emptyList())
    override val users: StateFlow<List<UserEntity>>
        get() = _users

    init {
        fetchUsers()
    }

    override fun navigateToSecond(bundle: SecondBundle) {
        viewModelScope.launch {
            _navigator.emit(NavigationEvent.Second(bundle))
        }
    }

    override fun navigateToCompose() {
        viewModelScope.launch {
            _navigator.emit(NavigationEvent.Compose)
        }
    }

    private fun fetchUsers() {
        showLoading()
        execute {
            when (val result = getUsersUseCase.execute(page = 1, size = 10)) {
                is co.nimblehq.domain.usecase.UseCaseResult.Success -> _users.value = result.data
                is co.nimblehq.domain.usecase.UseCaseResult.Error -> _error.emit(result.exception.message.orEmpty())
            }
            hideLoading()
        }
    }
}
