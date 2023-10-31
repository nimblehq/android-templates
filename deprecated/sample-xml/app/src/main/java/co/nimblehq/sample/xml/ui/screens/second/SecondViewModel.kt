package co.nimblehq.sample.xml.ui.screens.second

import co.nimblehq.sample.xml.model.UiModel
import co.nimblehq.sample.xml.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor() : BaseViewModel() {

    private val _id = MutableStateFlow<String?>(null)
    val id: StateFlow<String?>
        get() = _id

    fun initViewModel(uiModel: UiModel) = launch { _id.emit(uiModel.id) }
}
