package co.nimblehq.coroutine.ui.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import co.nimblehq.coroutine.databinding.FragmentHomeBinding
import co.nimblehq.coroutine.databinding.ViewLoadingBinding
import co.nimblehq.coroutine.domain.data.entity.UserEntity
import co.nimblehq.coroutine.extension.visibleOrGone
import co.nimblehq.coroutine.lib.IsLoading
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

        binding.btNext.setOnClickListener {
            viewModel.navigateToSecond(SecondBundle("From home"))
        }
    }

    override fun bindViewModel() {
        viewModel.users bindTo ::displayUsers
        viewModel.showLoading bindTo ::bindLoading
        viewModel.error bindTo toaster::display
        viewModel.navigator bindTo navigator::navigate
    }

    private fun displayUsers(users: List<UserEntity>) {
        Timber.d("Result : $users")
    }

    private fun bindLoading(isLoading: IsLoading) {
        viewLoadingBinding.pbLoading.visibleOrGone(isLoading)
    }
}
