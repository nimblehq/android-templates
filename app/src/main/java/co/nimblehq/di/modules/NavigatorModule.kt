package co.nimblehq.di.modules

import co.nimblehq.ui.screens.MainNavigator
import co.nimblehq.ui.screens.MainNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
abstract class NavigatorModule {

    @Binds
    abstract fun mainNavigator(mainNavigator: MainNavigatorImpl): MainNavigator
}
