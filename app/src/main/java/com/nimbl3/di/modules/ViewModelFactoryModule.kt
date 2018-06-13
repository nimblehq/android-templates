package com.nimbl3.di.modules

import android.arch.lifecycle.ViewModelProvider
import com.nimbl3.lib.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}