package co.nimblehq.ui.screens.second

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import co.nimblehq.R
import co.nimblehq.extension.subscribeOnClick
import co.nimblehq.ui.base.BaseFragment
import co.nimblehq.ui.screens.home.Data
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * TODO update with a new permission requesting
 */
//@RuntimePermissions
@AndroidEntryPoint
class SecondFragment : BaseFragment() {

    override val layoutRes: Int = R.layout.fragment_second

    private val viewModel by viewModels<SecondViewModel>()

    private val args: SecondFragmentArgs by navArgs()

    override fun setupView() {
        btOpenCamera
            .subscribeOnClick { toaster.display("Not yet implemented") }
            .addToDisposables()
    }

    override fun bindViewModel() {
        viewModel.input.dataFromIntent(args.bundle.data)

        viewModel.output.persistData bindTo ::bindPersistedData
    }

//    @NeedsPermission(Manifest.permission.CAMERA)
//    fun openCamera() {
//        startActivity(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
//    }
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
//        onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }

    private fun bindPersistedData(data: Data) {
        // TODO: Refactor view's naming
        persistTextView.text = data.content
    }
}
