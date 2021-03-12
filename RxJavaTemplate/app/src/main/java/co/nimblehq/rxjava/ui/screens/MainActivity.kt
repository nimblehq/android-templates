package co.nimblehq.rxjava.ui.screens

import androidx.activity.viewModels
import co.nimblehq.rxjava.R
import co.nimblehq.rxjava.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override val layoutRes: Int = R.layout.activity_main

    private val viewModel by viewModels<MainViewModel>()

    override fun bindViewModel() {}
}
