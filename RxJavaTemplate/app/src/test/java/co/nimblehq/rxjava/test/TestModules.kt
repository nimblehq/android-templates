package co.nimblehq.rxjava.test

import android.content.Context
import co.nimblehq.rxjava.di.modules.*
import co.nimblehq.rxjava.domain.schedulers.*
import co.nimblehq.rxjava.ui.common.Toaster
import co.nimblehq.rxjava.ui.screens.MainNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk

@Module
@TestInstallIn(
    components = [FragmentComponent::class],
    replaces = [NavigatorModule::class]
)
object TestNavigatorModule {
    val mockMainNavigator = mockk<MainNavigator>()

    @Provides
    fun provideMainNavigator() = mockMainNavigator
}

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {
    val mockToaster = mockk<Toaster>()
    val testSchedulerProvider = TrampolineSchedulerProvider
    val mockContext = mockk<Context>()

    @Provides
    fun provideToaster() = mockToaster

    @Provides
    fun provideContext() = mockContext

    @Provides
    fun provideSchedulerProvider(): BaseSchedulerProvider = testSchedulerProvider
}
