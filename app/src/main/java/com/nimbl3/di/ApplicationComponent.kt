package com.nimbl3.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import com.nimbl3.TemplateApplication
import com.nimbl3.di.modules.*
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
                      AppModule::class,
                      RetrofitModule::class,
                      GsonModule::class,
                      OkHttpClientModule::class,
                      SchedulersModule::class,
                      ActivityBuilderModule::class])
interface ApplicationComponent : AndroidInjector<TemplateApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TemplateApplication>()
}
