package co.nimblehq.rxjava.di.modules

import android.content.Context
import co.nimblehq.rxjava.RxJavaTemplateApplication
import co.nimblehq.rxjava.domain.schedulers.BaseSchedulerProvider
import co.nimblehq.rxjava.domain.schedulers.SchedulerProvider
import co.nimblehq.rxjava.ui.common.Toaster
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideContext(application: RxJavaTemplateApplication): Context = application

    @Provides
    fun toaster(@ApplicationContext context: Context): Toaster = Toaster(context)

    @Provides
    fun schedulerProvider(): BaseSchedulerProvider = SchedulerProvider()
}
