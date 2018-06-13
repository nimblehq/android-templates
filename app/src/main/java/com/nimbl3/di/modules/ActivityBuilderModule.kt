package com.nimbl3.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.nimbl3.MainActivity
import com.nimbl3.ui.main.di.MainActivityModule

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity
}
