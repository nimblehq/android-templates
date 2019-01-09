package com.nimbl3.ui.main

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View.*
import com.jakewharton.rxbinding2.view.RxView
import com.nimbl3.R
import com.nimbl3.data.lib.schedulers.SchedulersProvider
import com.nimbl3.extension.loadImage
import com.nimbl3.lib.IsLoading
import com.nimbl3.ui.base.BaseActivity
import com.nimbl3.ui.main.data.Data
import com.nimbl3.ui.second.SecondActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var schedulers: SchedulersProvider

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

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

        RxView.clicks(buttonRefresh)
            .subscribe { viewModel.inputs.refresh() }
            .bindForDisposable()

        RxView.clicks(buttonNext)
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
