package com.nimbl3.ui.second

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.nimbl3.R
import com.nimbl3.data.lib.schedulers.SchedulersProvider
import com.nimbl3.ui.base.BaseActivity
import com.nimbl3.ui.main.Const
import com.nimbl3.ui.main.data.Data
import com.nimbl3.ui.second.adapter.MyAdapter
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
        setupRecyclerView()

        bindViewModel()
        viewModel.intent(intent)
    }

    private fun bindViewModel() {
        viewModel.outputs
            .setPersistedData()
            .observeOn(schedulers.main())
            .subscribe { bindRecyclerView(it) }
            .bindForDisposable()
    }

    private fun setupRecyclerView() {
        val viewManager = LinearLayoutManager(this)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
        }
    }

    private fun bindRecyclerView(content: List<String>) {
        val viewAdapter = MyAdapter(
            content.toTypedArray(),
            object : MyAdapter.OnItemClickListener {
                override fun onItemClick(item: String) {
                    // TODO: bind this to ViewModel to resolve the CTA, DO NOT call to CTA directly
                    Toast.makeText(this@SecondActivity, item, Toast.LENGTH_SHORT).show()
                }
            })
        recyclerView.adapter = viewAdapter
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
