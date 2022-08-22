package co.nimblehq.sample.xml.ui.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import co.nimblehq.sample.xml.databinding.FragmentHomeBinding
import co.nimblehq.sample.xml.extension.provideViewModels
import co.nimblehq.sample.xml.model.UiModel
import co.nimblehq.sample.xml.ui.base.BaseFragment
import co.nimblehq.sample.xml.ui.common.ItemDivider
import co.nimblehq.sample.xml.ui.screens.MainNavigator
import co.nimblehq.sample.xml.ui.screens.home.adapter.ItemListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel: HomeViewModel by provideViewModels()
    private val itemListAdapter: ItemListAdapter by lazy { ItemListAdapter() }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = { inflater, container, attachToParent ->
            FragmentHomeBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        binding.rvHome.apply {
            adapter = itemListAdapter.apply {
                onItemClicked = { uiModel -> viewModel.navigateToSecond(uiModel) }
            }
            addItemDecoration(ItemDivider(this.context))
        }
    }

    override fun bindViewModel() {
        viewModel.uiModels bindTo ::displayUiModels
        viewModel.error bindTo toaster::display
        viewModel.navigator bindTo navigator::navigate
    }

    private fun displayUiModels(uiModels: List<UiModel>) {
        itemListAdapter.submitList(uiModels)
    }
}
