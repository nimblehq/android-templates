package co.nimblehq.sample.compose.ui.screens.main.second

import co.nimblehq.sample.compose.ui.base.BaseDestination
import co.nimblehq.sample.compose.ui.base.BaseViewModel
import co.nimblehq.sample.compose.ui.base.KeyResultOk
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor() :
    BaseViewModel<SecondViewState, SecondViewIntent, SecondViewEffect>(SecondViewState) {

    override fun onIntent(intent: SecondViewIntent) {
        when (intent) {
            is SecondViewIntent.UpdateClick -> navigateUpWithResult()
        }
    }

    private fun navigateUpWithResult() {
        sendViewEffect(
            SecondViewEffect.Navigate(BaseDestination.Up().addResult(KeyResultOk, true))
        )
    }
}
