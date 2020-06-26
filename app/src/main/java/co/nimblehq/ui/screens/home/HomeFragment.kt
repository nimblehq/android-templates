package co.nimblehq.ui.screens.home

import co.nimblehq.R
import co.nimblehq.ui.base.BaseFragment

class HomeFragment : BaseFragment<HomeViewModel>() {

    override val viewModelClass = HomeViewModel::class

    override val layoutRes = R.layout.fragment_main

    override fun setupView() {}

    override fun bindViewModel() {}

}
