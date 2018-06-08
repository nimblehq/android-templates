package com.nimbl3.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.nimbl3.MainActivity

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}
