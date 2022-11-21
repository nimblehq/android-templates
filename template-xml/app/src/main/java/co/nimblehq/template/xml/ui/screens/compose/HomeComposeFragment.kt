package co.nimblehq.template.xml.ui.screens.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource
import co.nimblehq.template.xml.R
import co.nimblehq.template.xml.extension.provideViewModels
import co.nimblehq.template.xml.ui.base.BaseComposeFragment
import co.nimblehq.template.xml.ui.screens.MainNavigator
import co.nimblehq.template.xml.ui.screens.compose.composables.HomeScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalComposeUiApi
@AndroidEntryPoint
class HomeComposeFragment : BaseComposeFragment() {

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel: HomeComposeViewModel by provideViewModels()

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