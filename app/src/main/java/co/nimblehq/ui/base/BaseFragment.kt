package co.nimblehq.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import co.nimblehq.domain.schedulers.SchedulerProvider
import co.nimblehq.extension.hideSoftKeyboard
import co.nimblehq.extension.subscribeOnClick
import co.nimblehq.extension.userReadableMessage
import co.nimblehq.ui.common.Toaster
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

abstract class BaseFragment : Fragment(), BaseFragmentCallbacks {

    @Inject
    lateinit var toaster: Toaster

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    protected abstract val layoutRes: Int

    private val disposables = CompositeDisposable()

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this as? BaseFragmentCallbacks)?.let { initViewModel() }
    }

    override fun initViewModel() {}

    @CallSuper
    override fun bindViewEvents() {
        requireNotNull(view)
            .subscribeOnClick {
                requireActivity().hideSoftKeyboard()
            }
            .addToDisposables()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (this as? BaseFragmentCallbacks)?.let {
            setupView()
            bindViewEvents()
            bindViewModel()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    open fun displayError(error: Throwable) {
        val message = error.userReadableMessage(requireContext())
        toaster.display(message)
    }

    protected fun Disposable.addToDisposables() = addTo(disposables)

    protected inline infix fun <T> Observable<T>.bindTo(crossinline action: (T) -> Unit) {
        observeOn(schedulerProvider.main())
            .subscribe { action(it) }
            .addToDisposables()
    }

}
