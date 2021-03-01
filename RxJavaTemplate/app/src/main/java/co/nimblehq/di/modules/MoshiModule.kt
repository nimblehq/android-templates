package co.nimblehq.di.modules

import co.nimblehq.data.service.providers.MoshiBuilderProvider
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class MoshiModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = MoshiBuilderProvider.moshiBuilder.build()
}
