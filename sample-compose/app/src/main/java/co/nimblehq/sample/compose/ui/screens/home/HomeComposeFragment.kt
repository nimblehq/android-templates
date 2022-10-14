package co.nimblehq.sample.compose.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import co.nimblehq.sample.compose.extension.provideViewModels
import co.nimblehq.sample.compose.ui.base.BaseComposeFragment
import co.nimblehq.sample.compose.ui.screens.MainNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeComposeFragment : BaseComposeFragment() {

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel: HomeComposeViewModel by provideViewModels()

    override val composeScreen: @Composable () -> Unit
        get() = {
            HomeComposeScreen(
                uiModels = viewModel.uiModels.collectAsState().value,
                showLoading = viewModel.showLoading.collectAsState().value,
                onItemClick = { uiModel -> viewModel.navigateToSecond(uiModel) }
            )
        }

    override fun bindViewModel() {
        viewModel.error bindTo ::displayError
        viewModel.navigator bindTo navigator::navigate
    }
}
