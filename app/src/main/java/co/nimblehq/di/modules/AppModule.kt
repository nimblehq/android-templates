package co.nimblehq.di.modules

import android.content.Context
import co.nimblehq.TemplateApplication
import co.nimblehq.domain.schedulers.BaseSchedulerProvider
import co.nimblehq.domain.schedulers.SchedulerProvider
import co.nimblehq.ui.common.Toaster
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
class AppModule {

    @Provides
    fun provideContext(application: TemplateApplication): Context = application

    @Provides
    fun schedulerProvider(): BaseSchedulerProvider = SchedulerProvider()

    // FIXME update with hilt config
    @Provides
    fun toaster(context: Context): Toaster = Toaster(context)
}
