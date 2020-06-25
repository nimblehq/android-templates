package co.nimblehq.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import co.nimblehq.di.ActivityViewModelFactory
import co.nimblehq.extension.userReadableMessage
import co.nimblehq.ui.common.Toaster
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseActivity<VM : BaseViewModel> : DaggerAppCompatActivity() {

    @Inject lateinit var viewModelFactory: ActivityViewModelFactory
    @Inject lateinit var toaster: Toaster

    protected val viewModel: VM by lazy { viewModel() }

    protected abstract val viewModelClass: KClass<VM>

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

    private fun viewModel(): VM =
        ViewModelProvider(this, viewModelFactory)[viewModelClass.java]

    protected inline infix fun <T> Observable<T>.bindTo(crossinline action: (T) -> Unit) {
        observeOn(AndroidSchedulers.mainThread())
            .subscribe { action(it) }
            .addToDisposables()
    }

    fun displayError(error: Throwable) {
        val message = error.userReadableMessage(this)
        toaster.display(message)
    }
}
