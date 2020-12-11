package co.nimblehq.di.modules

import co.nimblehq.ui.screens.MainNavigator
import co.nimblehq.ui.screens.MainNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
abstract class NavigatorModule {

    @Binds
    abstract fun bindMainNavigator(mainNavigator: MainNavigatorImpl): MainNavigator
}
