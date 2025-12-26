package co.nimblehq.sample.compose.di.modules.main

import androidx.hilt.navigation.compose.hiltViewModel
import co.nimblehq.sample.compose.navigation.EntryProviderInstaller
import co.nimblehq.sample.compose.navigation.Navigator
import co.nimblehq.sample.compose.navigation.NavigatorImpl
import co.nimblehq.sample.compose.ui.screens.details.DetailsScreen
import co.nimblehq.sample.compose.ui.screens.details.DetailsScreenUi
import co.nimblehq.sample.compose.ui.screens.details.DetailsViewModel
import co.nimblehq.sample.compose.ui.screens.list.ListScreen
import co.nimblehq.sample.compose.ui.screens.list.ListScreenUi
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
    fun provideNavigator(): Navigator = NavigatorImpl(startDestination = ListScreen)

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
            entry<DetailsScreen> { key ->
                val viewModel = hiltViewModel<DetailsViewModel, DetailsViewModel.Factory>(
                    // Note: We need a new ViewModel for every new DetailsScreen instance. Usually
                    // we would need to supply a `key` String that is unique to the
                    // instance, however, the ViewModelStoreNavEntryDecorator (supplied
                    // above) does this for us, using `NavEntry.contentKey` to uniquely
                    // identify the viewModel.
                    //
                    // tl;dr: Make sure you use rememberViewModelStoreNavEntryDecorator()
                    // if you want a new ViewModel for each new navigation key instance.
                    creationCallback = { factory ->
                        factory.create(key)
                    }
                )
                DetailsScreenUi(
                    viewModel = viewModel,
                    onClickBack = navigator::goBack
                )
            }
        }
}
