package co.nimblehq.sample.compose.di.modules.main

import androidx.hilt.navigation.compose.hiltViewModel
import co.nimblehq.sample.compose.ui.screens.detail.DetailScreen
import co.nimblehq.sample.compose.ui.screens.detail.DetailScreenUi
import co.nimblehq.sample.compose.ui.screens.list.ListScreen
import co.nimblehq.sample.compose.ui.screens.list.ListScreenUi
import co.nimblehq.sample.compose.util.EntryProviderInstaller
import co.nimblehq.sample.compose.util.Navigator
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
    fun provideNavigator() : Navigator = Navigator(startDestination = ListScreen)

    @IntoSet
    @Provides
    fun provideEntryProviderInstaller(navigator: Navigator): EntryProviderInstaller =
        {
            entry<ListScreen> {
                ListScreenUi(
                    viewModel = hiltViewModel(),
                    onItemClick = { detailScreen ->
                        navigator.goTo(detailScreen)
                    }
                )
            }
            entry<DetailScreen> { key ->
                DetailScreenUi()
            }
        }
}
