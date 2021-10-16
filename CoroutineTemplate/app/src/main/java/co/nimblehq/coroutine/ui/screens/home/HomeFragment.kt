package co.nimblehq.coroutine.ui.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import co.nimblehq.coroutine.databinding.FragmentHomeBinding
import co.nimblehq.coroutine.databinding.ViewLoadingBinding
import co.nimblehq.coroutine.extension.visibleOrGone
import co.nimblehq.coroutine.lib.IsLoading
import co.nimblehq.coroutine.model.UserUiModel
import co.nimblehq.coroutine.ui.base.BaseFragment
import co.nimblehq.coroutine.ui.screens.MainNavigator
import co.nimblehq.coroutine.ui.screens.second.SecondBundle
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var viewLoadingBinding: ViewLoadingBinding

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = { inflater, container, attachToParent ->
            FragmentHomeBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        viewLoadingBinding = ViewLoadingBinding.bind(binding.root)
    }

    override fun bindViewEvents() {
        super.bindViewEvents()

        with(binding) {
            btNext.setOnClickListener {
                viewModel.navigateToSecond(SecondBundle("From home"))
            }

            btCompose.setOnClickListener {
                viewModel.navigateToCompose()
            }
        }
    }

    override fun bindViewModel() {
        viewModel.userUiModels bindTo ::displayUsers
        viewModel.showLoading bindTo ::bindLoading
        viewModel.error bindTo toaster::display
        viewModel.navigator bindTo navigator::navigate
    }

    private fun displayUsers(users: List<UserUiModel>) {
        Timber.d("Result : $users")
    }

    private fun bindLoading(isLoading: IsLoading) {
        viewLoadingBinding.pbLoading.visibleOrGone(isLoading)
    }
}
