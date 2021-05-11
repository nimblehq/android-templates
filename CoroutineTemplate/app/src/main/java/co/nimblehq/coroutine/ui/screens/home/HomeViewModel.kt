package co.nimblehq.coroutine.ui.screens.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.nimblehq.coroutine.domain.data.entity.UserEntity
import co.nimblehq.coroutine.domain.usecase.ChatUseCaseResult
import co.nimblehq.coroutine.domain.usecase.GetUserUseCase
import co.nimblehq.coroutine.lib.Event
import co.nimblehq.coroutine.ui.base.BaseViewModel
import kotlinx.coroutines.launch

interface Input {

    fun users(): LiveData<List<UserEntity>>

    fun showError(): LiveData<Event<String>>
}

class HomeViewModel @ViewModelInject constructor(
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel(), Input {

    private val _users = MutableLiveData<List<UserEntity>>()
    private val _showError = MutableLiveData<Event<String>>()

    override fun users(): LiveData<List<UserEntity>> = _users

    override fun showError(): LiveData<Event<String>> = _showError

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            when (val result = getUserUseCase.execute()) {
                is ChatUseCaseResult.Success -> _users.value = result.data
                is ChatUseCaseResult.Error -> _showError.value = Event(result.exception.message ?: "")
            }
        }
    }
}
