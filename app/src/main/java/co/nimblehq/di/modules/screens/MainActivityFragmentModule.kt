package co.nimblehq.di.modules.screens

import co.nimblehq.di.FragmentScope
import co.nimblehq.ui.screens.home.HomeFragment
import co.nimblehq.ui.screens.second.SecondFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainActivityFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    fun homeFragment(): HomeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SecondFragmentModule::class])
    fun secondFragment(): SecondFragment

}
