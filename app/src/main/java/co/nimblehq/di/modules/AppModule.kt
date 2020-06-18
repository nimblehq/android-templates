package co.nimblehq.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import co.nimblehq.TemplateApplication
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
class AppModule {

    @Provides
    fun provideContext(application: TemplateApplication): Context = application
}
