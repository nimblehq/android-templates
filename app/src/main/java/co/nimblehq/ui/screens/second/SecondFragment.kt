package co.nimblehq.ui.screens.second

import android.Manifest
import android.content.Intent
import android.provider.MediaStore
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import co.nimblehq.R
import co.nimblehq.domain.data.Data
import co.nimblehq.extension.subscribeOnClick
import co.nimblehq.ui.base.BaseFragment
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_second.*
import javax.inject.Inject

@AndroidEntryPoint
class SecondFragment : BaseFragment() {

    @Inject
    lateinit var rxPermissions: RxPermissions

    override val layoutRes: Int = R.layout.fragment_second

    private val viewModel by viewModels<SecondViewModel>()

    private val args: SecondFragmentArgs by navArgs()

    override fun setupView() {
        btOpenCamera
            .subscribeOnClick(::requestCamera)
            .addToDisposables()
    }

    override fun bindViewModel() {
        viewModel.input.dataFromIntent(args.bundle.data)

        viewModel.output.persistData bindTo ::bindPersistedData
    }

    private fun requestCamera() {
        rxPermissions
            .requestEach(Manifest.permission.CAMERA)
            .subscribe(::handleCameraPermission)
            .addToDisposables()
    }

    private fun handleCameraPermission(permission: Permission) {
        when {
            permission.granted -> {
                startActivity(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
            }
            permission.shouldShowRequestPermissionRationale -> {
                // Deny
                toaster.display("Permission camera denied")
            }
            else -> {
                // Deny and never ask again
                toaster.display("Permission camera never ask again")
            }
        }
    }

    private fun bindPersistedData(data: Data) {
        // TODO: Refactor view's naming
        persistTextView.text = data.content
    }
}
