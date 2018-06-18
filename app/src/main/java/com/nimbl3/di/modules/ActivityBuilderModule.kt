package com.nimbl3.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.nimbl3.MainActivity
import com.nimbl3.ui.main.di.MainActivityModule
import com.nimbl3.ui.second.SecondActivity
import com.nimbl3.ui.second.di.SecondActivityModule

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [SecondActivityModule::class])
    abstract fun bindSecondActivity(): SecondActivity
}
