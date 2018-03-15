package template.nimbl3.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import template.nimbl3.ui.MainActivity

// Add all Activity that it need to use dependencies.
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}
