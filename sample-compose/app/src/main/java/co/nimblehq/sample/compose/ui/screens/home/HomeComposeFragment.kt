package co.nimblehq.sample.compose.ui.screens.home

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import co.nimblehq.sample.compose.extension.provideViewModels
import co.nimblehq.sample.compose.ui.base.BaseComposeFragment
import co.nimblehq.sample.compose.ui.screens.MainNavigator
import com.markodevcic.peko.PermissionResult
import com.markodevcic.peko.PermissionResult.Denied
import com.markodevcic.peko.PermissionResult.Granted
import com.markodevcic.peko.requestPermissionsAsync
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeComposeFragment : BaseComposeFragment() {

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel: HomeComposeViewModel by provideViewModels()

    override val composeScreen: @Composable () -> Unit
        get() = {
            HomeComposeScreen(
                uiModels = viewModel.uiModels.collectAsState().value,
                showLoading = viewModel.showLoading.collectAsState().value,
                onItemClick = { uiModel -> viewModel.navigateToSecond(uiModel) }
            )
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA
        )
    }

    override fun bindViewModel() {
        viewModel.error bindTo ::displayError
        viewModel.navigator bindTo navigator::navigate
    }

    private fun requestPermissions(vararg permissions: String) {
        lifecycleScope.launch {
            val result = requestPermissionsAsync(*permissions)
            onPermissionResult(result)
        }
    }

    private fun onPermissionResult(permissionResult: PermissionResult) {
        when (permissionResult) {
            is Granted -> Timber.d("${permissionResult.grantedPermissions} granted")
            is Denied.NeedsRationale -> Timber.d("${permissionResult.deniedPermissions} needs rationale")
            else -> Timber.d("Request cancelled, missing permissions in manifest or denied permanently")
        }
    }
}
