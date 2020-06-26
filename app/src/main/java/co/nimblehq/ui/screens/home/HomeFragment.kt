package co.nimblehq.ui.screens.home

import co.nimblehq.R
import co.nimblehq.extension.loadImage
import co.nimblehq.extension.subscribeOnClick
import co.nimblehq.extension.visibleOrGone
import co.nimblehq.extension.visibleOrInvisible
import co.nimblehq.lib.IsLoading
import co.nimblehq.ui.base.BaseFragment
import co.nimblehq.ui.screens.MainNavigator
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class HomeFragment : BaseFragment<HomeViewModel>() {

    override val viewModelClass = HomeViewModel::class

    override val layoutRes = R.layout.fragment_main

    @Inject
    lateinit var navigator: MainNavigator

    override fun setupView() {
        buttonRefresh
            .subscribeOnClick { viewModel.input.refresh() }
            .addToDisposables()

        buttonNext
            .subscribeOnClick { viewModel.input.next() }
            .addToDisposables()
    }

    override fun bindViewModel() {
        viewModel.loadData bindTo ::bindData
        viewModel.showLoading bindTo ::showLoading
        viewModel.navigator bindTo navigator::navigate
    }

    private fun bindData(data: Data) {
        textView.text = data.content
        imageView.loadImage(data.imageUrl)
    }

    private fun showLoading(isLoading: IsLoading) {
        buttonRefresh.visibleOrInvisible(!isLoading)
        progressBar.visibleOrGone(isLoading)
    }

}
