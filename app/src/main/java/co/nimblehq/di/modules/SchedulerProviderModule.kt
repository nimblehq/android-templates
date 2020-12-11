package co.nimblehq.di.modules

import co.nimblehq.domain.schedulers.BaseSchedulerProvider
import co.nimblehq.domain.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
class SchedulerProviderModule {

    @Provides
    fun schedulerProvider(): BaseSchedulerProvider = SchedulerProvider()
}
