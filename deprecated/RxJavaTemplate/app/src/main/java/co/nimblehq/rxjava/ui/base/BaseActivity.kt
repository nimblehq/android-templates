package co.nimblehq.rxjava.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import co.nimblehq.rxjava.domain.schedulers.SchedulerProvider
import co.nimblehq.rxjava.ui.common.Toaster
import co.nimblehq.rxjava.ui.userReadableMessage
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    @Inject
    lateinit var toaster: Toaster

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    protected abstract val bindingInflater: (LayoutInflater) -> VB

    private var _binding: ViewBinding? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindingInflater.invoke(layoutInflater).apply {
            _binding = this
        }.root)

        bindViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
        _binding = null
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
