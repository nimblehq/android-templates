package com.nimbl3

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import com.nimbl3.di.DaggerApplicationComponent
import timber.log.Timber

class TemplateApplication: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<TemplateApplication> =
            DaggerApplicationComponent.builder().create(this)

    override fun onCreate() {
        super.onCreate()
        plantTimber()
    }

    private fun plantTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
