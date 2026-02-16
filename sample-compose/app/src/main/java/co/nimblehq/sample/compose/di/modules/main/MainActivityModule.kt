package co.nimblehq.sample.compose.di.modules.main

import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.extensions.showToast
import co.nimblehq.sample.compose.navigation.EntryProviderInstaller
import co.nimblehq.sample.compose.navigation.Navigator
import co.nimblehq.sample.compose.navigation.NavigatorImpl
import co.nimblehq.sample.compose.ui.screens.main.MainDestination
import co.nimblehq.sample.compose.ui.screens.main.home.HomeScreen
import co.nimblehq.sample.compose.ui.screens.main.second.SecondScreen
import co.nimblehq.sample.compose.ui.screens.main.third.ThirdScreen
import co.nimblehq.sample.compose.util.LocalResultEventBus
import co.nimblehq.sample.compose.util.ResultEffect
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object MainActivityModule {

    @Provides
    @ActivityRetainedScoped
    fun provideNavigator(): Navigator = NavigatorImpl(startDestination = MainDestination.Home)

    @IntoSet
    @Provides
    fun provideEntryProviderInstaller(navigator: Navigator): EntryProviderInstaller =
        {
            entry<MainDestination.Home> {
                val eventBus = LocalResultEventBus.current
                val context = LocalContext.current

                ResultEffect<Boolean> { isUpdated ->
                    if (isUpdated) {
                        context.showToast(context.getString(R.string.message_updated))
                    }
                    eventBus.removeResult<Boolean>()
                }

                HomeScreen(
                    viewModel = hiltViewModel(),
                    navigator = navigator
                )
            }

            entry<MainDestination.Second> { key ->
                val eventBus = LocalResultEventBus.current

                SecondScreen(
                    id = key.id,
                    navigator = navigator,
                    viewModel = hiltViewModel(),
                    onUpdate = {
                        eventBus.sendResult<Boolean>(result = true)
                        navigator.goBack()
                    }
                )
            }

            entry<MainDestination.Third> { key ->
                ThirdScreen(
                    model = key.model,
                    navigator = navigator,
                    viewModel = hiltViewModel()
                )
            }
        }
}
