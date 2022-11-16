package co.nimblehq.template.xml.ui.screens

import co.nimblehq.template.xml.ui.base.BaseViewModel
import co.nimblehq.template.xml.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers)
