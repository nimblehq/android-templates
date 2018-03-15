package template.nimbl3

import android.app.Application
import template.nimbl3.di.AppModule
import template.nimbl3.di.ApplicationComponent
import template.nimbl3.di.DaggerApplicationComponent

class TemplateApplication: Application() {

    companion object {
        lateinit var appComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}