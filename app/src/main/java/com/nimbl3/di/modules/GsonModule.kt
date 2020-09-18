package com.nimbl3.di.modules

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class GsonModule {
    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()
}