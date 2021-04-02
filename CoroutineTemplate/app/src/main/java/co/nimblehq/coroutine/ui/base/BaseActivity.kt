package co.nimblehq.coroutine.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import co.nimblehq.coroutine.ui.common.Toaster
import co.nimblehq.coroutine.ui.userReadableMessage
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject lateinit var toaster: Toaster

    @get:LayoutRes
    protected abstract val layoutRes: Int

    protected abstract val viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
    }

    fun displayError(error: Throwable) {
        val message = error.userReadableMessage(this)
        toaster.display(message)
    }
}
