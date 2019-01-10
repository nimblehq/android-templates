package com.nimbl3.di.modules

import com.nimbl3.ui.main.MainActivity
import com.nimbl3.ui.main.di.MainActivityModule
import com.nimbl3.ui.second.SecondActivity
import com.nimbl3.ui.second.di.SecondActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [SecondActivityModule::class])
    abstract fun bindSecondActivity(): SecondActivity
}
