package co.nimblehq.coroutine.ui.screens.second

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import co.nimblehq.coroutine.R
import co.nimblehq.coroutine.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_second.*

@AndroidEntryPoint
class SecondFragment : BaseFragment() {

    override val layoutRes: Int = R.layout.fragment_second

    private val viewModel by viewModels<SecondViewModel>()

    private val args: SecondFragmentArgs by navArgs()

    override fun setupView() {
        tvMessage.text = args.bundle.message
    }

    override fun bindViewModel() {}
}
