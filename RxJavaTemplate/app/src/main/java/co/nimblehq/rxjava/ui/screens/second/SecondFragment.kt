package co.nimblehq.rxjava.ui.screens.second

import android.Manifest
import android.content.Intent
import android.provider.MediaStore
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import co.nimblehq.rxjava.R
import co.nimblehq.rxjava.domain.data.Data
import co.nimblehq.rxjava.extension.loadImage
import co.nimblehq.rxjava.extension.subscribeOnClick
import co.nimblehq.rxjava.ui.base.BaseFragment
import co.nimblehq.rxjava.ui.helpers.handleVisualOverlaps
import co.nimblehq.rxjava.ui.screens.MainNavigator
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_second.*
import javax.inject.Inject

@AndroidEntryPoint
class SecondFragment : BaseFragment() {

    @Inject
    lateinit var navigator: MainNavigator

    @Inject
    lateinit var rxPermissions: RxPermissions

    private val viewModel by viewModels<SecondViewModel>()
    private val args: SecondFragmentArgs by navArgs()

    override val layoutRes: Int = R.layout.fragment_second

    override fun setupView() {
        btOpenCamera
            .subscribeOnClick(::requestCamera)
            .addToDisposables()

        btOpenPost
            .subscribeOnClick(viewModel::openPost)
            .addToDisposables()
    }

    override fun handleVisualOverlaps() {
        btOpenCamera.handleVisualOverlaps()
        btOpenPost.handleVisualOverlaps()
    }

    override fun bindViewModel() {
        viewModel.navigator bindTo navigator::navigate
        viewModel.data bindTo ::bindData

        viewModel.input.dataFromIntent(args.bundle.data)
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

    private fun bindData(data: Data) {
        with(data) {
            tvSecondTitle.text = title
            tvSecondAuthor.text = author
            ivSecondThumbnail.loadImage(thumbnail)
        }
    }
}
