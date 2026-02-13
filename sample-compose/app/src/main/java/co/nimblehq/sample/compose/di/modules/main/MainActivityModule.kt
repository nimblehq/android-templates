package co.nimblehq.sample.compose.di.modules.main

import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import co.nimblehq.sample.compose.navigation.EntryProviderInstaller
import co.nimblehq.sample.compose.navigation.Navigator
import co.nimblehq.sample.compose.navigation.NavigatorImpl
import co.nimblehq.sample.compose.ui.common.PATH_BASE
import co.nimblehq.sample.compose.ui.common.PATH_SEARCH
import co.nimblehq.sample.compose.ui.screens.details.DetailsScreen
import co.nimblehq.sample.compose.ui.screens.details.DetailsViewModel
import co.nimblehq.sample.compose.ui.screens.list.ListScreen
import co.nimblehq.sample.compose.ui.screens.login.LoginOrRegisterScreen
import co.nimblehq.sample.compose.ui.screens.login.LoginScreen
import co.nimblehq.sample.compose.ui.screens.search.SearchScreen
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
    fun provideNavigator(): Navigator = NavigatorImpl(startDestination = ListScreen)

    @Suppress("LongMethod")
    @IntoSet
    @Provides
    fun provideEntryProviderInstaller(navigator: Navigator): EntryProviderInstaller =
        {
            entry<ListScreen> {
                ListScreen(
                    viewModel = hiltViewModel(),
                    onClickSearch = { navigator.goTo(SearchScreen) },
                    onItemClick = { uiModel ->
                        navigator.goTo(DetailsScreen.Details(uiModel.id.toIntOrNull() ?: 1))
                    }
                )
            }

            entry<SearchScreen> {
                val context = LocalContext.current
                SearchScreen(
                    onClickCreateDeeplink = { username ->
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            "$PATH_BASE/$PATH_SEARCH?${DetailsScreen.Search::username.name}=${Uri.encode(username)}".toUri()
                        )
                        context.startActivity(intent)
                    },
                    onClickBack = navigator::goBack
                )
            }

            entry<DetailsScreen.Details> { key ->
                val viewModel = hiltViewModel<DetailsViewModel, DetailsViewModel.Factory>(
                    // Note: We need a new ViewModel for every new DetailsScreen instance. Usually
                    // we would need to supply a `key` String that is unique to the
                    // instance, however, the ViewModelStoreNavEntryDecorator (supplied
                    // above) does this for us, using `NavEntry.contentKey` to uniquely
                    // identify the viewModel.
                    //
                    // tl;dr: Make sure you use rememberViewModelStoreNavEntryDecorator()
                    // if you want a new ViewModel for each new navigation key instance.
                    creationCallback = { factory -> factory.create(key) }
                )
                val eventBus = LocalResultEventBus.current

                ResultEffect<String> { username ->
                    viewModel.changeUsername(username)
                    eventBus.removeResult<String>()
                }

                DetailsScreen(
                    viewModel = viewModel,
                    navigateToLoginOrRegister = { navigator.goTo(LoginOrRegisterScreen) },
                    onClickBack = navigator::goBack
                )
            }

            entry<DetailsScreen.Search> { key ->
                val viewModel = hiltViewModel<DetailsViewModel, DetailsViewModel.Factory>(
                    creationCallback = { factory -> factory.create(key) }
                )

                DetailsScreen(
                    viewModel = viewModel,
                    navigateToLoginOrRegister = { navigator.goTo(LoginOrRegisterScreen) },
                    onClickBack = navigator::goBack
                )
            }

            entry<LoginOrRegisterScreen> {
                LoginOrRegisterScreen(
                    navigateToLogin = { navigator.goTo(LoginScreen) },
                    navigateToRegister = { /* NO-OP */ },
                    onClickBack = navigator::goBack
                )
            }

            entry<LoginScreen> {
                val eventBus = LocalResultEventBus.current
                LoginScreen(
                    navigateToDetails = { username ->
                        eventBus.sendResult<String>(result = username)
                        navigator.goBackToLast(DetailsScreen::class)
                    },
                    onClickBack = navigator::goBack
                )
            }
        }
}
