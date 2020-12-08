package co.nimblehq.ui.second

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.viewModels
import co.nimblehq.R
import co.nimblehq.extension.subscribeOnClick
import co.nimblehq.ui.base.BaseActivity
import co.nimblehq.ui.main.Const
import co.nimblehq.ui.main.data.Data
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_second.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions

@AndroidEntryPoint
@RuntimePermissions
class SecondActivity : BaseActivity() {

    override val layoutRes: Int = R.layout.activity_second

    private val viewModel by viewModels<SecondViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btOpenCamera
            .subscribeOnClick { openCameraWithPermissionCheck() }
            .addToDisposables()
    }

    override fun bindViewModel() {
        viewModel.inputs.dataFromIntent(intent.getParcelableExtra(Const.EXTRAS_DATA))

        viewModel.outputs.setPersistedData() bindTo ::bindPersistedData
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun bindPersistedData(data: Data) {
        persistTextView.text = data.content
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
