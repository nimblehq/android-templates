package com.nimbl3.di.modules

import com.nimbl3.data.lib.schedulers.SchedulersProvider
import com.nimbl3.data.lib.schedulers.SchedulersProviderImpl
import dagger.Module
import dagger.Provides

@Module
class SchedulersModule {
    @Provides
    fun provideSchedulersProvider(): SchedulersProvider = SchedulersProviderImpl()
}
