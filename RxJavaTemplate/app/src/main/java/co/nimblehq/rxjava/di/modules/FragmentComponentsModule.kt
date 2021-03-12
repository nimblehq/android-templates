package co.nimblehq.rxjava.di.modules

import androidx.fragment.app.Fragment
import co.nimblehq.rxjava.ui.screens.MainNavigator
import co.nimblehq.rxjava.ui.screens.MainNavigatorImpl
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
abstract class NavigatorModule {

    @Binds
    abstract fun mainNavigator(mainNavigator: MainNavigatorImpl): MainNavigator
}

@InstallIn(FragmentComponent::class)
@Module
class RxPermissionsModule {

    @Provides
    fun rxPermissions(fragment: Fragment): RxPermissions = RxPermissions(fragment)
}
