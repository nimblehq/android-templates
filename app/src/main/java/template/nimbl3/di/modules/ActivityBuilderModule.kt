package template.nimbl3.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import template.nimbl3.ui.MainActivity

// Add the Activities that need dependencies injection with @ContributesAndroidInjector
@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}
