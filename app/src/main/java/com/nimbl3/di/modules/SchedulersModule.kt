package com.nimbl3.di.modules

import dagger.Module
import dagger.Provides
import com.nimbl3.data.lib.schedulers.SchedulersProvider
import com.nimbl3.data.lib.schedulers.SchedulersProviderImpl

@Module
class SchedulersModule {
     @Provides
    fun provideSchedulersProvider(): SchedulersProvider = SchedulersProviderImpl()
}