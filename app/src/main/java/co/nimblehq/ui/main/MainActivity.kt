package co.nimblehq.ui.main

import android.os.Bundle
import android.view.View.*
import androidx.activity.viewModels
import co.nimblehq.R
import co.nimblehq.extension.loadImage
import co.nimblehq.extension.subscribeOnClick
import co.nimblehq.lib.IsLoading
import co.nimblehq.ui.base.BaseActivity
import co.nimblehq.ui.main.data.Data
import co.nimblehq.ui.second.SecondActivity
import com.jakewharton.rxbinding3.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override val layoutRes: Int = R.layout.activity_main

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        buttonRefresh
            .subscribeOnClick { viewModel.inputs.refresh() }
            .addToDisposables()

        buttonNext
            .subscribeOnClick { viewModel.inputs.next() }
            .addToDisposables()
    }

    override fun bindViewModel() {
        viewModel.outputs.loadData() bindTo ::bindData
        viewModel.outputs.isLoading() bindTo ::showLoading
        viewModel.outputs.gotoNextScreen() bindTo ::gotoNextScreen
    }

    private fun bindData(data: Data) {
        textView.text = data.content
        imageView.loadImage(data.imageUrl)
    }

    private fun showLoading(isLoading: IsLoading) {
        buttonRefresh.visibility = if (isLoading) INVISIBLE else VISIBLE
        progressBar.visibility = if (isLoading) VISIBLE else GONE
    }

    private fun gotoNextScreen(data: Data) {
        SecondActivity.show(this, data)
    }
}
