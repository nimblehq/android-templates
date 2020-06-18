package com.nimbl3.di.modules

import dagger.Module
import dagger.Provides
import co.nimblehq.data.lib.schedulers.SchedulersProvider
import co.nimblehq.data.lib.schedulers.SchedulersProviderImpl
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
class SchedulersModule {
     @Provides
    fun provideSchedulersProvider(): SchedulersProvider = SchedulersProviderImpl()
}
