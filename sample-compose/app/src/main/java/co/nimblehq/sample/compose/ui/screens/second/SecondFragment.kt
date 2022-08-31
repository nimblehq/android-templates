package co.nimblehq.sample.compose.ui.screens.second

import androidx.compose.runtime.Composable
import androidx.navigation.fragment.navArgs
import co.nimblehq.sample.compose.ui.base.BaseComposeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondFragment : BaseComposeFragment() {

    private val args by navArgs<SecondFragmentArgs>()

    override val composeScreen: @Composable () -> Unit
        get() = { SecondScreen(args.uiModel) }

    override fun bindViewModel() {
        // Do nothing
    }
}
