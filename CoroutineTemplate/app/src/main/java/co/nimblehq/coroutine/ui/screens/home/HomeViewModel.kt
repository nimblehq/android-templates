package co.nimblehq.coroutine.ui.screens.home

import androidx.lifecycle.viewModelScope
import co.nimblehq.coroutine.domain.data.entity.UserEntity
import co.nimblehq.coroutine.domain.usecase.GetUsersUseCase
import co.nimblehq.coroutine.domain.usecase.UseCaseResult
import co.nimblehq.coroutine.ui.base.BaseViewModel
import co.nimblehq.coroutine.ui.base.NavigationEvent
import co.nimblehq.coroutine.ui.screens.second.SecondBundle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface Output {

    val users: StateFlow<List<UserEntity>>

    fun navigateToSecond(bundle: SecondBundle)
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel(), Output {

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

    private fun fetchUsers() {
        showLoading()
        viewModelScope.launch {
            when (val result = getUsersUseCase.execute()) {
                is UseCaseResult.Success -> _users.value = result.data
                is UseCaseResult.Error -> _error.emit(result.exception.message.orEmpty())
            }
            hideLoading()
        }
    }
}
