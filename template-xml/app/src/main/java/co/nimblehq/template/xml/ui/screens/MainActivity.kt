package co.nimblehq.template.xml.ui.screens

import android.view.LayoutInflater
import androidx.activity.viewModels
import co.nimblehq.template.xml.databinding.ActivityMainBinding
import co.nimblehq.template.xml.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = { inflater -> ActivityMainBinding.inflate(inflater) }

    override val viewModel by viewModels<MainViewModel>()
}
