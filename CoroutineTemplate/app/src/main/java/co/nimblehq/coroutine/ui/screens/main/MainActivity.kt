package co.nimblehq.coroutine.ui.screens.main

import androidx.activity.viewModels
import co.nimblehq.coroutine.R
import co.nimblehq.coroutine.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override val layoutResource: Int = R.layout.activity_main

    override val viewModel: MainViewModel by viewModels()

    override fun initView() {
        tvTitle.text = viewModel.title
    }
}
