package template.nimbl3

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import template.nimbl3.di.DaggerApplicationComponent

class TemplateApplication: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<TemplateApplication> =
            DaggerApplicationComponent.builder().create(this)

}
