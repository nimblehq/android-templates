package co.nimblehq.rxjava.ui.base

import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.View.*
import androidx.annotation.CallSuper
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import co.nimblehq.rxjava.domain.schedulers.BaseSchedulerProvider
import co.nimblehq.rxjava.domain.schedulers.SchedulerProvider
import co.nimblehq.rxjava.extension.hideSoftKeyboard
import co.nimblehq.rxjava.extension.subscribeOnClick
import co.nimblehq.rxjava.ui.common.Toaster
import co.nimblehq.rxjava.ui.userReadableMessage
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

@SuppressWarnings("TooManyFunctions")
abstract class BaseFragment<VB : ViewBinding> : Fragment(), BaseFragmentCallbacks {

    @Inject
    lateinit var toaster: Toaster

    @Inject
    lateinit var schedulerProvider: BaseSchedulerProvider

    private var _binding: ViewBinding? = null

    @Suppress("UNCHECKED_CAST")
    val binding: VB
        get() = _binding as VB

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    /**
     * https://developer.android.com/training/system-ui/immersive#EnableFullscreen
     */
    protected val fullScreenSystemUiVisibility = SYSTEM_UI_FLAG_IMMERSIVE or
        SYSTEM_UI_FLAG_LAYOUT_STABLE or
        SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
        SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

    protected open val systemUiVisibility = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> {
            fullScreenSystemUiVisibility or
                SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
        else -> {
            SYSTEM_UI_FLAG_VISIBLE
        }
    }

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
        return bindingInflater.invoke(inflater, container, false).apply {
            _binding = this
        }.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (this as? BaseFragmentCallbacks)?.let {
            setWindowStyle()
            setupView()
            handleVisualOverlaps()
            bindViewEvents()
            bindViewModel()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
        _binding = null
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

    private fun setWindowStyle() {
        requireActivity().window.decorView.run {
            systemUiVisibility = this@BaseFragment.systemUiVisibility
        }
    }
}
