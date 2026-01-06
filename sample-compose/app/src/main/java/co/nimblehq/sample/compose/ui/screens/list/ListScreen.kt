package co.nimblehq.sample.compose.ui.screens.list

import android.Manifest.permission.CAMERA
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.extensions.collectAsEffect
import co.nimblehq.sample.compose.extensions.showToast
import co.nimblehq.sample.compose.lib.IsLoading
import co.nimblehq.sample.compose.ui.base.BaseScreen
import co.nimblehq.sample.compose.ui.common.AppBar
import co.nimblehq.sample.compose.ui.models.UiModel
import co.nimblehq.sample.compose.ui.screens.details.DetailsScreen
import co.nimblehq.sample.compose.ui.showToast
import co.nimblehq.sample.compose.ui.theme.ComposeTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

data object ListScreen: NavKey

@Composable
fun ListScreenUi(
    viewModel: ListViewModel,
    onClickSearch: () -> Unit,
    onItemClick: (goTo: DetailsScreen) -> Unit
) = BaseScreen(
    isDarkStatusBarIcons = true,
) {

    val context = LocalContext.current
    viewModel.error.collectAsEffect { e -> e.showToast(context) }

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

    ListScreenUiContent(
        uiModels = uiModels,
        isLoading = isLoading,
        onClickSearch = onClickSearch,
        onItemClick = { model ->
            onItemClick(DetailsScreen.Details(id = model.id.toInt()))
        },
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
private fun ListScreenUiContent(
    uiModels: List<UiModel>,
    isLoading: IsLoading,
    onClickSearch: () -> Unit,
    onItemClick: (UiModel) -> Unit,
) {
    Scaffold(
        topBar = {
            AppBar(
                title = R.string.list_title,
                actions = {
                    IconButton(
                        onClick = onClickSearch
                    ) {
                        Icon(Icons.Outlined.Search, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
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
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ListScreenUiContentPreview() {
    ComposeTheme {
        ListScreenUiContent(
            uiModels = listOf(UiModel("1", "name1"), UiModel("2", "name2"), UiModel("3", "name3")),
            isLoading = false,
            onClickSearch = {},
            onItemClick = {},
        )
    }
}
