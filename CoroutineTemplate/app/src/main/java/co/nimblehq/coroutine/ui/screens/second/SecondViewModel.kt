package co.nimblehq.coroutine.ui.screens.second

import co.nimblehq.coroutine.dispatcher.DispatchersProvider
import co.nimblehq.coroutine.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(dispatchers: DispatchersProvider) :
    BaseViewModel(dispatchers)
