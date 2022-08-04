package co.nimblehq.sample.xml.ui.screens.second

import android.view.LayoutInflater
import android.view.ViewGroup
import co.nimblehq.sample.xml.databinding.FragmentHomeBinding
import co.nimblehq.sample.xml.extension.provideViewModels
import co.nimblehq.sample.xml.model.UiModel
import co.nimblehq.sample.xml.ui.base.BaseFragment
import co.nimblehq.sample.xml.ui.screens.MainNavigator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SecondFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel: SecondViewModel by provideViewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = { inflater, container, attachToParent ->
            FragmentHomeBinding.inflate(inflater, container, attachToParent)
        }

    override fun bindViewModel() {
        viewModel.uiModels bindTo ::displayUiModels
        viewModel.error bindTo toaster::display
        viewModel.navigator bindTo navigator::navigate
    }

    private fun displayUiModels(uiModels: List<UiModel>) {
        Timber.d("Result : $uiModels")
    }
}
