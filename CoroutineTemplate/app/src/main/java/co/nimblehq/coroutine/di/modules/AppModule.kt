package co.nimblehq.coroutine.di.modules

import android.content.Context
import co.nimblehq.coroutine.TemplateApplication
import co.nimblehq.coroutine.ui.common.Toaster
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideContext(application: TemplateApplication): Context = application

    @Provides
    fun toaster(@ApplicationContext context: Context): Toaster = Toaster(context)
}
