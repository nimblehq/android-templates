package co.nimblehq.sample.xml.ui.screens.second

import co.nimblehq.sample.xml.model.UiModel
import co.nimblehq.sample.xml.ui.base.BaseViewModel
import co.nimblehq.sample.xml.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers) {

    private val _id = MutableStateFlow<String?>(null)
    val id: StateFlow<String?>
        get() = _id

    fun initViewModel(uiModel: UiModel) = execute { _id.emit(uiModel.id) }
}
