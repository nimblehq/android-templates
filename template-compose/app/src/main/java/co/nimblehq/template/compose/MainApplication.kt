package co.nimblehq.template.compose

import android.app.Application
import co.nimblehq.template.compose.data.di.initKoin
import co.nimblehq.template.compose.di.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(appModule + localModule + httpClientModule + viewModelModule)
        }
        setupLogging()
    }

    private fun setupLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
