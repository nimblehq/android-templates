package co.nimblehq.template.compose.di.modules.main

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import co.nimblehq.template.compose.navigation.EntryProviderInstaller
import co.nimblehq.template.compose.navigation.Navigator
import co.nimblehq.template.compose.navigation.NavigatorImpl
import co.nimblehq.template.compose.ui.screens.main.home.Home
import co.nimblehq.template.compose.ui.screens.main.home.HomeScreen
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
    fun provideNavigator(): Navigator = NavigatorImpl(startDestination = Home)

    @IntoSet
    @Provides
    fun provideEntryProviderInstaller(navigator: Navigator): EntryProviderInstaller = {
        entry<Home> {
            HomeScreen(
                viewModel = hiltViewModel(),
                navigator = navigator
            )
        }
    }
}
