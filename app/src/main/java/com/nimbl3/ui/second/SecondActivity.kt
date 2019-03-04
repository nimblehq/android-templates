package com.nimbl3.ui.second

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.nimbl3.R
import com.nimbl3.data.lib.schedulers.SchedulersProvider
import com.nimbl3.ui.base.BaseActivity
import com.nimbl3.ui.main.Const
import com.nimbl3.ui.main.data.Data
import kotlinx.android.synthetic.main.activity_second.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions
import javax.inject.Inject


@RuntimePermissions
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

        btOpenCamera.setOnClickListener { openCameraWithPermissionCheck() }
    }

    private fun bindViewModel() {
        viewModel.outputs
            .setPersistedData()
            .observeOn(schedulers.main())
            .subscribe { persistTextView.text = it.content }
            .bindForDisposable()
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    fun openCamera() {
        startActivity(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun showDeniedForCamera() {
        Toast.makeText(this, "Permission camera denied", Toast.LENGTH_SHORT).show()
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun showNeverAskForCamera() {
        Toast.makeText(this, "Permission camera never ask", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
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
