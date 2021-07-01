package co.nimblehq.rxjava.ui.screens

import android.view.LayoutInflater
import androidx.activity.viewModels
import co.nimblehq.rxjava.databinding.ActivityMainBinding
import co.nimblehq.rxjava.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        { inflater -> ActivityMainBinding.inflate(inflater) }

    private val viewModel by viewModels<MainViewModel>()

    override fun bindViewModel() {}
}
