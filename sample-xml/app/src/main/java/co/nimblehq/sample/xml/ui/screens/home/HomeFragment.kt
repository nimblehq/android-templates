package co.nimblehq.sample.xml.ui.screens.home

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.CAMERA
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import co.nimblehq.common.extensions.visibleOrGone
import co.nimblehq.sample.xml.databinding.FragmentHomeBinding
import co.nimblehq.sample.xml.extension.provideViewModels
import co.nimblehq.sample.xml.lib.IsLoading
import co.nimblehq.sample.xml.model.UiModel
import co.nimblehq.sample.xml.ui.base.BaseFragment
import co.nimblehq.sample.xml.ui.screens.MainNavigator
import co.nimblehq.sample.xml.ui.screens.home.adapter.ItemListAdapter
import com.markodevcic.peko.PermissionResult
import com.markodevcic.peko.PermissionResult.Denied
import com.markodevcic.peko.PermissionResult.Granted
import com.markodevcic.peko.requestPermissionsAsync
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
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
            itemListAdapter.onItemClicked = { uiModel -> viewModel.navigateToSecond(uiModel) }
            adapter = itemListAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayout.HORIZONTAL))
            layoutManager = GridLayoutManager(context, 2, RecyclerView.HORIZONTAL, false)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    adjustSeekBar()

                    binding.seekbar.progress = computeHorizontalScrollOffset()
                    Timber.d("Seekbar progress = ${binding.seekbar.progress}")
                }
            })
        }
        requestPermissions(ACCESS_FINE_LOCATION, CAMERA)
    }

    private fun adjustSeekBar() {
        with(binding.seekbar) {
            max = binding.rvHome.computeHorizontalScrollRange() - binding.rvHome.computeHorizontalScrollExtent()
            Timber.d("Seekbar max = $max")
        }
    }

    override fun bindViewModel() {
        viewModel.uiModels bindTo ::displayUiModels
        viewModel.error bindTo ::displayError
        viewModel.navigator bindTo navigator::navigate
        viewModel.showLoading bindTo ::showLoading
    }

    private fun showLoading(isShow: IsLoading) {
        binding.pbHome.visibleOrGone(isShow)
    }

    private fun displayUiModels(uiModels: List<UiModel>) {
        itemListAdapter.submitList(uiModels)
    }

    private fun requestPermissions(vararg permissions: String) {
        lifecycleScope.launch {
            val result = requestPermissionsAsync(*permissions)
            handlePermissionResult(result)
        }
    }

    private fun handlePermissionResult(permissionResult: PermissionResult) {
        when (permissionResult) {
            is Granted -> Timber.d("${permissionResult.grantedPermissions} granted")
            is Denied.NeedsRationale -> Timber.d("${permissionResult.deniedPermissions} needs rationale")
            else -> Timber.d("Request cancelled, missing permissions in manifest or denied permanently")
        }
    }
}
