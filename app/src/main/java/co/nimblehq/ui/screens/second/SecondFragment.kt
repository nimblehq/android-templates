package co.nimblehq.ui.screens.second

import android.content.Intent
import android.provider.MediaStore
import co.nimblehq.R
import co.nimblehq.ui.base.BaseFragment
import co.nimblehq.ui.screens.home.Data
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * TODO update with a new permission requesting
 */
//@RuntimePermissions
class SecondFragment : BaseFragment<SecondViewModel>() {

    override val viewModelClass = SecondViewModel::class

    override val layoutRes: Int = R.layout.fragment_second

    override fun setupView() {
        btOpenCamera.setOnClickListener { openCamera() }
    }

    override fun bindViewModel() {
        viewModel.setPersistedData() bindTo ::bindPersistedData
    }

    //    @NeedsPermission(Manifest.permission.CAMERA)
    fun openCamera() {
        startActivity(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
    }
//
//    @OnPermissionDenied(Manifest.permission.CAMERA)
//    fun showDeniedForCamera() {
//        Toast.makeText(this, "Permission camera denied", Toast.LENGTH_SHORT).show()
//    }
//
//    @OnNeverAskAgain(Manifest.permission.CAMERA)
//    fun showNeverAskForCamera() {
//        Toast.makeText(this, "Permission camera never ask", Toast.LENGTH_SHORT).show()
//    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        onRequestPermissionsResult(requestCode, grantResults)
//    }

    private fun bindPersistedData(data: Data) {
        persistTextView.text = data.content
    }

}
