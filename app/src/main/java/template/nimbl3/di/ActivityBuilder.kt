package template.nimbl3.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import template.nimbl3.ui.MainActivity

// Add the Activities that need dependencies injection with @ContributesAndroidInjector
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}
