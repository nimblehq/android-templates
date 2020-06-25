package co.nimblehq.ui.base

import android.os.Bundle
import android.view.*
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModelProvider
import co.nimblehq.di.ActivityViewModelFactory
import co.nimblehq.di.FragmentViewModelFactory
import co.nimblehq.extension.*
import co.nimblehq.ui.common.Toaster
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseFragment<VM : BaseViewModel> : DaggerFragment(), BaseFragmentCallbacks {

    @Inject lateinit var viewModelFactory: FragmentViewModelFactory

    @Inject lateinit var activityViewModelFactory: ActivityViewModelFactory

    @Inject lateinit var toaster: Toaster

    protected val viewModel: VM by lazy { viewModel() }

    protected abstract val viewModelClass: KClass<VM>

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

    protected open fun displayError(error: Throwable) {
        val message = error.userReadableMessage(requireContext())
        toaster.display(message)
    }

    protected fun Disposable.addToDisposables() = addTo(disposables)

    private fun viewModel(): VM =
        ViewModelProvider(this, viewModelFactory)[viewModelClass.java]

    protected inline infix fun <T> Observable<T>.bindTo(crossinline action: (T) -> Unit) {
        observeOn(AndroidSchedulers.mainThread())
            .subscribe { action(it) }
            .addToDisposables()
    }

}
