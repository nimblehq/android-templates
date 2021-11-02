package co.nimblehq.coroutine.ui.screens.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.fragment.app.viewModels
import co.nimblehq.coroutine.ui.base.BaseComposeFragment
import co.nimblehq.coroutine.ui.screens.compose.composables.ComposeScreen
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalComposeUiApi
@AndroidEntryPoint
class ComposeFragment : BaseComposeFragment() {

    private val viewModel: ComposeViewModel by viewModels()

    override val composeScreen: @Composable () -> Unit
        get() = {
            with(viewModel) {
                ComposeScreen(
                    userUiModels = userUiModels.collectAsState().value,
                    showLoading = showLoading.collectAsState().value,
                    textFieldValue = textFieldValue.value,
                    onTextFieldValueChange = ::updateTextFieldValue,
                    onUserItemClick = toaster::display
                )
            }
        }

    override fun bindViewModel() {
        viewModel.error bindTo toaster::display
    }
}
