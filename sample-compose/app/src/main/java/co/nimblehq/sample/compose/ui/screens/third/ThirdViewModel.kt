package co.nimblehq.sample.compose.ui.screens.third

import androidx.lifecycle.SavedStateHandle
import co.nimblehq.sample.compose.ui.base.BaseViewModel
import co.nimblehq.sample.compose.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThirdViewModel @Inject constructor(
    dispatchers: DispatchersProvider,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(dispatchers) {}
