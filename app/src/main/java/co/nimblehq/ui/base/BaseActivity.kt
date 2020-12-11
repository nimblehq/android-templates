package co.nimblehq.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import co.nimblehq.domain.schedulers.SchedulerProvider
import co.nimblehq.extension.userReadableMessage
import co.nimblehq.ui.common.Toaster
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var toaster: Toaster

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    @get:LayoutRes
    protected abstract val layoutRes: Int

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)

        bindViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    abstract fun bindViewModel()

    protected fun Disposable.addToDisposables() = addTo(disposables)

    protected inline infix fun <T> Observable<T>.bindTo(crossinline action: (T) -> Unit) {
        observeOn(schedulerProvider.main())
            .subscribe { action(it) }
            .addToDisposables()
    }

    fun displayError(error: Throwable) {
        val message = error.userReadableMessage(this)
        toaster.display(message)
    }
}
