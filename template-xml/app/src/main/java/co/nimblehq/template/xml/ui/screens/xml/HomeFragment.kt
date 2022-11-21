package co.nimblehq.template.xml.ui.screens.xml

import android.view.LayoutInflater
import android.view.ViewGroup
import co.nimblehq.template.xml.databinding.FragmentHomeBinding
import co.nimblehq.template.xml.extension.provideViewModels
import co.nimblehq.template.xml.model.UiModel
import co.nimblehq.template.xml.ui.base.BaseFragment
import co.nimblehq.template.xml.ui.screens.MainNavigator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel: HomeViewModel by provideViewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = { inflater, container, attachToParent ->
            FragmentHomeBinding.inflate(inflater, container, attachToParent)
        }

    override fun bindViewModel() {
        viewModel.uiModels bindTo ::displayUiModels
        viewModel.error bindTo ::displayError
        viewModel.navigator bindTo navigator::navigate
    }

    private fun displayUiModels(uiModels: List<UiModel>) {
        Timber.d("Result : $uiModels")
    }
}
