package co.nimblehq.coroutine.ui.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.nimblehq.coroutine.domain.data.entity.UserEntity
import co.nimblehq.coroutine.domain.usecase.UseCaseResult
import co.nimblehq.coroutine.domain.usecase.GetUsersUseCase
import co.nimblehq.coroutine.lib.Event
import co.nimblehq.coroutine.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

interface Output {

    val users: LiveData<List<UserEntity>>

    val showError: LiveData<Event<String>>
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel(), Output {

    // TODO: Re-factor LiveData to Flow and remove Event
    private val _users = MutableLiveData<List<UserEntity>>()
    override val users: LiveData<List<UserEntity>>
        get() = _users

    private val _showError = MutableLiveData<Event<String>>()
    override val showError: LiveData<Event<String>>
        get() = _showError

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            when (val result = getUsersUseCase.execute()) {
                is UseCaseResult.Success -> _users.value = result.data
                is UseCaseResult.Error -> _showError.value = Event(result.exception.message.orEmpty())
            }
        }
    }
}
