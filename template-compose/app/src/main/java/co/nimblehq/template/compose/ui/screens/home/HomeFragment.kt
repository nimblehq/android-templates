package co.nimblehq.template.compose.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource
import co.nimblehq.template.compose.R
import co.nimblehq.template.compose.extension.provideViewModels
import co.nimblehq.template.compose.ui.base.BaseFragment
import co.nimblehq.template.compose.ui.screens.MainNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalComposeUiApi
@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel: HomeViewModel by provideViewModels()

    override val composeScreen: @Composable () -> Unit
        get() = {
            HomeScreen(
                title = stringResource(id = R.string.app_name),
                uiModels = viewModel.uiModels.collectAsState().value
            )
        }

    override fun bindViewModel() {
        viewModel.error bindTo ::displayError
        viewModel.navigator bindTo navigator::navigate
    }
}
