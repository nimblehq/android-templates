package co.nimblehq.di.modules

import android.content.Context
import co.nimblehq.TemplateApplication
import co.nimblehq.ui.common.Toaster
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ApplicationComponent::class)
@Module
class AppModule {

    @Provides
    fun provideContext(application: TemplateApplication): Context = application

    @Provides
    fun toaster(@ApplicationContext context: Context): Toaster = Toaster(context)
}
