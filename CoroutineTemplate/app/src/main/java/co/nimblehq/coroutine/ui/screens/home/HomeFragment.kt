package co.nimblehq.coroutine.ui.screens.home

import androidx.fragment.app.viewModels
import co.nimblehq.coroutine.R
import co.nimblehq.coroutine.ui.base.BaseFragment
import co.nimblehq.coroutine.ui.base.NavigationEvent
import co.nimblehq.coroutine.ui.screens.MainNavigator
import co.nimblehq.coroutine.ui.screens.second.SecondBundle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_home

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel by viewModels<HomeViewModel>()

    override fun setupView() {
        btNext.setOnClickListener {
            // TODO navigate through _navigator flow declared inside ViewModel instead
            navigator.navigate(NavigationEvent.Second(SecondBundle("From home")))
        }
    }

    override fun bindViewModel() {}
}
