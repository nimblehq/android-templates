package co.nimblehq.coroutine.ui.screens

import co.nimblehq.coroutine.dispatcher.DispatchersProvider
import co.nimblehq.coroutine.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(dispatchersProvider: DispatchersProvider) :
    BaseViewModel(dispatchersProvider)
