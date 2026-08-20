package co.nimblehq.sample.compose.ui.screens.main.home

import androidx.lifecycle.viewModelScope
import co.nimblehq.sample.compose.domain.usecases.GetModelsUseCase
import co.nimblehq.sample.compose.domain.usecases.IsFirstTimeLaunchPreferencesUseCase
import co.nimblehq.sample.compose.domain.usecases.UpdateFirstTimeLaunchPreferencesUseCase
import co.nimblehq.sample.compose.ui.base.BaseViewModel
import co.nimblehq.sample.compose.ui.models.UiModel
import co.nimblehq.sample.compose.ui.models.toUiModel
import co.nimblehq.sample.compose.ui.screens.main.MainDestination
import co.nimblehq.sample.compose.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getModelsUseCase: GetModelsUseCase,
    isFirstTimeLaunchPreferencesUseCase: IsFirstTimeLaunchPreferencesUseCase,
    private val updateFirstTimeLaunchPreferencesUseCase: UpdateFirstTimeLaunchPreferencesUseCase,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<HomeViewState, HomeViewIntent, HomeViewEffect>(HomeViewState()) {

    init {
        getModelsUseCase()
            .onStart { updateViewState { it.copy(isLoading = true) } }
            .onEach { result ->
                val uiModels = result.map { it.toUiModel() }.toImmutableList()
                updateViewState { it.copy(uiModels = uiModels) }
            }
            .onCompletion { updateViewState { it.copy(isLoading = false) } }
            .flowOn(dispatchersProvider.io)
            .catch { e -> sendViewEffect(HomeViewEffect.ShowError(e)) }
            .launchIn(viewModelScope)

        isFirstTimeLaunchPreferencesUseCase()
            .onEach { isFirstTimeLaunch ->
                if (isFirstTimeLaunch) {
                    sendViewEffect(HomeViewEffect.ShowFirstTimeLaunchMessage)
                    updateFirstTimeLaunchPreferencesUseCase(false)
                }
            }
            .flowOn(dispatchersProvider.io)
            .catch { e -> sendViewEffect(HomeViewEffect.ShowError(e)) }
            .launchIn(viewModelScope)
    }

    override fun onIntent(intent: HomeViewIntent) {
        when (intent) {
            is HomeViewIntent.ItemClick -> navigateToSecond(intent.uiModel)
            is HomeViewIntent.ItemLongClick -> navigateToThird(intent.uiModel)
        }
    }

    private fun navigateToSecond(uiModel: UiModel) {
        sendViewEffect(HomeViewEffect.Navigate(MainDestination.Second.createRoute(uiModel.id)))
    }

    private fun navigateToThird(uiModel: UiModel) {
        sendViewEffect(HomeViewEffect.Navigate(MainDestination.Third.addParcel(uiModel)))
    }
}
