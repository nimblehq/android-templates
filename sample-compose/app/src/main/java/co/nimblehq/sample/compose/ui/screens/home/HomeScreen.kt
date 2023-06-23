package co.nimblehq.sample.compose.ui.screens.home

import android.Manifest.permission.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.extensions.collectAsEffect
import co.nimblehq.sample.compose.extensions.showToast
import co.nimblehq.sample.compose.lib.IsLoading
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.ui.AppDestination
import co.nimblehq.sample.compose.ui.screens.AppBar
import co.nimblehq.sample.compose.ui.showToast
import co.nimblehq.sample.compose.ui.theme.ComposeTheme
import com.google.accompanist.permissions.*
import kotlinx.coroutines.flow.*

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: (destination: AppDestination) -> Unit,
) {
    val context = LocalContext.current
    viewModel.error.collectAsEffect { e -> e.showToast(context) }
    viewModel.navigator.collectAsEffect { destination -> navigator(destination) }

    val isLoading: IsLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val uiModels: List<UiModel> by viewModel.uiModels.collectAsStateWithLifecycle()
    val isFirstTimeLaunch: Boolean by viewModel.isFirstTimeLaunch.collectAsStateWithLifecycle()

    LaunchedEffect(isFirstTimeLaunch) {
        if (isFirstTimeLaunch) {
            context.showToast(context.getString(R.string.message_first_time_launch))
            viewModel.onFirstTimeLaunch()
        }
    }

    CameraPermission()

    HomeScreenContent(
        uiModels = uiModels,
        isLoading = isLoading,
        onItemClick = viewModel::navigateToSecond,
        onItemLongClick = viewModel::navigateToThird
    )
}

/**
 * [CameraPermission] needs to be separate from [HomeScreenContent] to avoid re-composition
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun CameraPermission() {
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(CAMERA)
    if (cameraPermissionState.status.isGranted) {
        context.showToast("${cameraPermissionState.permission} granted")
    } else {
        if (cameraPermissionState.status.shouldShowRationale) {
            context.showToast("${cameraPermissionState.permission} needs rationale")
        } else {
            context.showToast("Request cancelled, missing permissions in manifest or denied permanently")
        }

        LaunchedEffect(Unit) {
            cameraPermissionState.launchPermissionRequest()
        }
    }
}

@Composable
private fun HomeScreenContent(
    uiModels: List<UiModel>,
    isLoading: IsLoading,
    onItemClick: (UiModel) -> Unit,
    onItemLongClick: (UiModel) -> Unit,
) {
    Scaffold(topBar = {
        AppBar(R.string.home_title_bar)
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                ItemList(
                    uiModels = uiModels,
                    onItemClick = onItemClick,
                    onItemLongClick = onItemLongClick
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    ComposeTheme {
        HomeScreenContent(
            uiModels = listOf(UiModel("1"), UiModel("2"), UiModel("3")),
            isLoading = false,
            onItemClick = {},
            onItemLongClick = {}
        )
    }
}
