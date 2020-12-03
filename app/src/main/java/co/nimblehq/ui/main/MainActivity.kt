package co.nimblehq.ui.main

import android.os.Bundle
import android.view.View.*
import androidx.activity.viewModels
import com.jakewharton.rxbinding3.view.clicks
import co.nimblehq.R
import co.nimblehq.data.lib.schedulers.SchedulersProvider
import co.nimblehq.extension.loadImage
import co.nimblehq.lib.IsLoading
import co.nimblehq.ui.base.BaseActivity
import co.nimblehq.ui.main.data.Data
import co.nimblehq.ui.second.SecondActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject lateinit var schedulers: SchedulersProvider

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindToViewModel()
    }

    private fun bindToViewModel() {
        viewModel
            .outputs
            .loadData()
            .observeOn(schedulers.main())
            .subscribe(this::bindData)
            .bindForDisposable()

        viewModel
            .outputs
            .isLoading()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .subscribe(this::showLoading)
            .bindForDisposable()

        viewModel
            .outputs
            .gotoNextScreen()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .subscribe(this::gotoNextScreen)
            .bindForDisposable()

        buttonRefresh.clicks()
            .subscribe { viewModel.inputs.refresh() }
            .bindForDisposable()

        buttonNext.clicks()
            .subscribe { viewModel.inputs.next()}
            .bindForDisposable()
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
