package co.nimblehq.rxjava.ui.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.nimblehq.rxjava.databinding.FragmentHomeBinding
import co.nimblehq.rxjava.databinding.ViewLoadingBinding
import co.nimblehq.rxjava.domain.data.Data
import co.nimblehq.rxjava.extension.subscribeOnClick
import co.nimblehq.rxjava.extension.subscribeOnItemClick
import co.nimblehq.rxjava.extension.visibleOrGone
import co.nimblehq.rxjava.lib.IdlingResource
import co.nimblehq.rxjava.lib.IsLoading
import co.nimblehq.rxjava.ui.base.BaseFragment
import co.nimblehq.rxjava.ui.helpers.handleVisualOverlaps
import co.nimblehq.rxjava.ui.screens.MainNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var dataAdapter: DataAdapter
    private lateinit var viewLoadingBinding: ViewLoadingBinding

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = { inflater, container, attachToParent ->
            FragmentHomeBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        viewLoadingBinding = ViewLoadingBinding.bind(binding.root)
        setupDataList()

        binding.btHomeRefresh
            .subscribeOnClick { viewModel.input.refresh() }
            .addToDisposables()
    }

    override fun handleVisualOverlaps() {
        with(binding) {
            rvHomeData.handleVisualOverlaps(marginInsteadOfPadding = false)
            btHomeRefresh.handleVisualOverlaps()
        }
    }

    override fun bindViewEvents() {
        super.bindViewEvents()
        dataAdapter
            .itemClick
            .subscribeOnItemClick {
                when (it) {
                    is DataAdapter.OnItemClick.Item ->
                        viewModel.input.navigateToDetail(it.data)
                }
            }
            .addToDisposables()
    }

    override fun bindViewModel() {
        viewModel.showLoading bindTo ::showLoading
        viewModel.error bindTo ::displayError
        viewModel.data bindTo ::bindData
        viewModel.navigator bindTo navigator::navigate
    }

    private fun setupDataList() {
        with(binding.rvHomeData) {
            adapter = DataAdapter().also {
                dataAdapter = it
            }
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    RecyclerView.VERTICAL
                )
            )
        }
    }

    private fun bindData(data: List<Data>) {
        dataAdapter.items = data
    }

    private fun showLoading(isLoading: IsLoading) {
        binding.btHomeRefresh.isEnabled = !isLoading
        viewLoadingBinding.pbLoading.visibleOrGone(isLoading)

        // TODO Find a way to remove IdlingResource logic from production
        IdlingResource.increaseOrDecrease(isLoading)
    }
}
