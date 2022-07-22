package co.nimblehq.template.ui.screens

import co.nimblehq.template.ui.base.BaseViewModel
import co.nimblehq.template.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers)
