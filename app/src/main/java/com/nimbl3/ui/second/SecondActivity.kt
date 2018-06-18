package com.nimbl3.ui.second

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.nimbl3.R
import com.nimbl3.data.lib.schedulers.SchedulersProvider
import com.nimbl3.ui.base.BaseActivity
import com.nimbl3.ui.main.Const
import com.nimbl3.ui.main.data.Data
import kotlinx.android.synthetic.main.activity_second.*
import javax.inject.Inject

class SecondActivity : BaseActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var schedulers: SchedulersProvider

    private val viewModel: SecondViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(SecondViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        bindViewModel()

        viewModel.intent(intent)
    }

    private fun bindViewModel() {
        viewModel.outputs
            .setPersistedData()
            .observeOn(schedulers.main())
            .subscribe { persistTextView.text = it.content }
            .bindForDisposable()
    }

    companion object {
        @JvmStatic
        fun show(from: Context, data: Data) {
            val intent = Intent(from, SecondActivity::class.java)
            intent.putExtra(Const.EXTRAS_DATA, data)
            from.startActivity(intent)
        }
    }
}
