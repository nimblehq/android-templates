package template.nimbl3.di

import dagger.Component
import template.nimbl3.ui.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface ApplicationComponent {
    fun inject(main: MainActivity)
}