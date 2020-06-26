package co.nimblehq.ui.screens.home

import co.nimblehq.R
import co.nimblehq.extension.loadImage
import co.nimblehq.extension.subscribeOnClick
import co.nimblehq.extension.visibleOrGone
import co.nimblehq.extension.visibleOrInvisible
import co.nimblehq.lib.IsLoading
import co.nimblehq.ui.base.BaseFragment
import co.nimblehq.ui.screens.second.SecondActivity
import kotlinx.android.synthetic.main.fragment_main.*

class HomeFragment : BaseFragment<HomeViewModel>() {

    override val viewModelClass = HomeViewModel::class

    override val layoutRes = R.layout.fragment_main

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
        viewModel.gotoNextScreen bindTo ::gotoNextScreen
    }

    private fun bindData(data: Data) {
        textView.text = data.content
        imageView.loadImage(data.imageUrl)
    }

    private fun showLoading(isLoading: IsLoading) {
        buttonRefresh.visibleOrInvisible(!isLoading)
        progressBar.visibleOrGone(isLoading)
    }

    private fun gotoNextScreen(data: Data) {
        SecondActivity.show(requireActivity(), data)
    }

}
