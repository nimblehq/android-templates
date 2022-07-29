package co.nimblehq.template.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected abstract val viewModel: BaseViewModel

    protected abstract val bindingInflater: (LayoutInflater) -> VB

    private var _binding: ViewBinding? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindingInflater.invoke(layoutInflater).apply {
            _binding = this
        }.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
