package co.nimblehq.coroutine.ui.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import co.nimblehq.coroutine.databinding.FragmentHomeBinding
import co.nimblehq.coroutine.ui.base.BaseFragment
import co.nimblehq.coroutine.ui.base.NavigationEvent
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

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = { inflater, container, attachToParent ->
            FragmentHomeBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        binding.btNext.setOnClickListener {
            // TODO navigate through _navigator flow declared inside ViewModel instead
            navigator.navigate(NavigationEvent.Second(SecondBundle("From home")))
        }
    }

    override fun bindViewModel() {
        with(viewModel) {
            users().observe(viewLifecycleOwner) { users ->
                Timber.d("Result : $users")
            }

            showError().observe(viewLifecycleOwner) {
                it.proceedIfNotHandled()?.let { message ->
                    toaster.display(message = message)
                }
            }
        }
    }
}
