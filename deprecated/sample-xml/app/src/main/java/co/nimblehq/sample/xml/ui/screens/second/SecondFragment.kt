package co.nimblehq.sample.xml.ui.screens.second

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import co.nimblehq.sample.xml.R
import co.nimblehq.sample.xml.databinding.FragmentSecondBinding
import co.nimblehq.sample.xml.extension.provideNavArgs
import co.nimblehq.sample.xml.extension.provideViewModels
import co.nimblehq.sample.xml.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondFragment : BaseFragment<FragmentSecondBinding>() {

    private val viewModel: SecondViewModel by provideViewModels()
    private val args: SecondFragmentArgs by provideNavArgs()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSecondBinding
        get() = { inflater, container, attachToParent ->
            FragmentSecondBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        // Hide navigation button on toolbar
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setHomeButtonEnabled(false)
        }
    }

    override fun initViewModel() {
        viewModel.initViewModel(args.uiModel)
    }

    override fun bindViewModel() {
        viewModel.id bindTo ::displayId
    }

    private fun displayId(id: String?) {
        binding.tvSecondId.text = getString(R.string.second_id_title, id)
    }
}
