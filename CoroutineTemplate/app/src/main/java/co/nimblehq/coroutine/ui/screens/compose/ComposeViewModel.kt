package co.nimblehq.coroutine.ui.screens.compose

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import co.nimblehq.coroutine.model.UserUiModel
import co.nimblehq.coroutine.model.toUserUiModelList
import co.nimblehq.coroutine.ui.base.BaseViewModel
import co.nimblehq.coroutine.usecase.GetUsersUseCase
import co.nimblehq.coroutine.usecase.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface Output {

    val users: StateFlow<List<UserUiModel>>

    val textFieldValue: State<String>

    fun updateTextFieldValue(value: String)
}

@HiltViewModel
class ComposeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel(), Output {

    private val _users = MutableStateFlow<List<UserUiModel>>(emptyList())
    override val users: StateFlow<List<UserUiModel>>
        get() = _users

    private val _textFieldValue = mutableStateOf("")
    override val textFieldValue: State<String>
        get() = _textFieldValue

    init {
        fetchUsers()
    }

    override fun updateTextFieldValue(value: String) {
        _textFieldValue.value = value
    }

    private fun fetchUsers() {
        showLoading()
        execute({
            when (val result = getUsersUseCase.execute()) {
                is UseCaseResult.Success -> _users.value = result.data.toUserUiModelList()
                is UseCaseResult.Error -> _error.emit(result.exception.message.orEmpty())
            }
            hideLoading()
        })
    }
}
