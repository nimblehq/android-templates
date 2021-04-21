package co.nimblehq.coroutine.ui.base

import android.os.Bundle
import android.view.*
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import co.nimblehq.coroutine.extension.hideSoftKeyboard
import co.nimblehq.coroutine.ui.common.Toaster
import co.nimblehq.coroutine.ui.userReadableMessage
import javax.inject.Inject

abstract class BaseFragment : Fragment(), BaseFragmentCallbacks {

    @Inject
    lateinit var toaster: Toaster

    @get:LayoutRes
    protected abstract val layoutRes: Int

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this as? BaseFragmentCallbacks)?.let { initViewModel() }
    }

    override fun initViewModel() {}

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

    @CallSuper
    override fun bindViewEvents() {
        requireNotNull(view).setOnClickListener {
            requireActivity().hideSoftKeyboard()
        }
    }

    open fun displayError(error: Throwable) {
        val message = error.userReadableMessage(requireContext())
        toaster.display(message)
    }
}
