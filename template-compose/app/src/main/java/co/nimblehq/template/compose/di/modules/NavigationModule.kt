package co.nimblehq.template.compose.di.modules

import co.nimblehq.template.compose.navigation.entry.HomeDestination
import co.nimblehq.template.compose.navigation.entry.homeDestinationEntry
import co.nimblehq.template.compose.navigation.entry.listDestinationEntry
import co.nimblehq.template.compose.navigation.navigator.AppNavigator
import co.nimblehq.template.compose.navigation.navigator.AppNavigatorImpl
import co.nimblehq.template.compose.navigation.navigator.EntryProviderInstaller
import co.nimblehq.template.compose.navigation.navigator.Up
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
class NavigationModule {

    @Provides
    @ActivityRetainedScoped
    fun provideAppNavigator(): AppNavigator = AppNavigatorImpl(startDestination = HomeDestination)

    @IntoSet
    @Provides
    fun provideEntryProviderInstaller(navigator: AppNavigator): EntryProviderInstaller = {
        homeDestinationEntry(onNavigate = { destination -> if (destination is Up) navigator.goBack() else  navigator.goTo(destination)})
        listDestinationEntry(onNavigate = { destination -> if (destination is Up) navigator.goBack() else  navigator.goTo(destination) })
    }
}
