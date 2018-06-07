package template.nimbl3.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import template.nimbl3.TemplateApplication
import template.nimbl3.di.modules.*
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
                      AppModule::class,
                      NetworkModule::class,
                      GsonModule::class,
                      SchedulersModule::class,
                      ActivityBuilderModule::class])
interface ApplicationComponent : AndroidInjector<TemplateApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TemplateApplication>()
}
