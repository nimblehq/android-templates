package template.nimbl3.di.modules

import dagger.Module
import dagger.Provides
import nimbl3.com.data.lib.schedulers.SchedulersProvider
import nimbl3.com.data.lib.schedulers.SchedulersProviderImpl

@Module
class SchedulersModule {
     @Provides
    fun provideSchedulersProvider(): SchedulersProvider = SchedulersProviderImpl()
}