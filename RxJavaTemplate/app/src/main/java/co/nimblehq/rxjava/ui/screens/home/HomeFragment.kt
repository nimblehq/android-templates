package co.nimblehq.rxjava.ui.screens.home

import androidx.fragment.app.viewModels
import co.nimblehq.rxjava.R
import co.nimblehq.rxjava.domain.data.Data
import co.nimblehq.rxjava.extension.loadImage
import co.nimblehq.rxjava.extension.subscribeOnClick
import co.nimblehq.rxjava.extension.visibleOrGone
import co.nimblehq.rxjava.extension.visibleOrInvisible
import co.nimblehq.rxjava.lib.IsLoading
import co.nimblehq.rxjava.ui.base.BaseFragment
import co.nimblehq.rxjava.ui.screens.MainNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_main

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel by viewModels<HomeViewModel>()

    override fun setupView() {
        btRefresh
            .subscribeOnClick { viewModel.input.refresh() }
            .addToDisposables()

        btNext
            .subscribeOnClick { viewModel.input.next() }
            .addToDisposables()
    }

    override fun bindViewModel() {
        viewModel.showLoading bindTo ::showLoading
        viewModel.error bindTo ::displayError
        viewModel.data bindTo ::bindData
        viewModel.navigator bindTo navigator::navigate
    }

    private fun bindData(data: Data) {
        tvContent.text = data.content
        ivPreview.loadImage(data.imageUrl)
    }

    private fun showLoading(isLoading: IsLoading) {
        btRefresh.visibleOrInvisible(!isLoading)
        pbLoading.visibleOrGone(isLoading)
    }

}
