package co.nimblehq.template.compose.ui.screens

import co.nimblehq.template.compose.ui.base.BaseViewModel
import co.nimblehq.template.compose.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers)
