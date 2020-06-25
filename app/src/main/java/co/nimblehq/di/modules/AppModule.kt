package co.nimblehq.di.modules

import android.content.Context
import co.nimblehq.TemplateApplication
import co.nimblehq.domain.schedulers.BaseSchedulerProvider
import co.nimblehq.domain.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
class AppModule {

    @Provides
    fun provideContext(application: TemplateApplication): Context = application

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun schedulerProvider(): BaseSchedulerProvider = SchedulerProvider()
    }
}
