package co.nimblehq.sample.compose.ui.screens.list

import android.Manifest.permission.CAMERA
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.extensions.collectAsEffect
import co.nimblehq.sample.compose.extensions.showToast
import co.nimblehq.sample.compose.lib.IsLoading
import co.nimblehq.sample.compose.ui.base.BaseScreen
import co.nimblehq.sample.compose.ui.common.AppBar
import co.nimblehq.sample.compose.ui.models.UiModel
import co.nimblehq.sample.compose.ui.showToast
import co.nimblehq.sample.compose.ui.theme.ComposeTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
data object ListScreen : NavKey

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun ListScreen(
    onClickSearch: () -> Unit,
    onItemClick: (UiModel) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel(),
) = BaseScreen(
    isDarkStatusBarIcons = true,
) {
    val context = LocalContext.current
    viewModel.error.collectAsEffect { e -> e.showToast(context) }

    val isLoading: IsLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val uiModels: ImmutableList<UiModel> by viewModel.uiModels.collectAsStateWithLifecycle()
    val isFirstTimeLaunch: Boolean by viewModel.isFirstTimeLaunch.collectAsStateWithLifecycle()

    LaunchedEffect(isFirstTimeLaunch) {
        if (isFirstTimeLaunch) {
            context.showToast(context.getString(R.string.message_first_time_launch))
            viewModel.onFirstTimeLaunch()
        }
    }

    CameraPermission()

    ListScreenContent(
        uiModels = uiModels,
        isLoading = isLoading,
        onClickSearch = onClickSearch,
        onItemClick = onItemClick,
        modifier = modifier,
    )
}

@Composable
private fun ListScreenContent(
    uiModels: ImmutableList<UiModel>,
    isLoading: IsLoading,
    onClickSearch: () -> Unit,
    onItemClick: (UiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AppBar(
                title = R.string.list_title,
                actions = {
                    IconButton(onClick = onClickSearch) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search"
                        )
                    }
                }
            )
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
                )
            }
        }
    }
}

/**
 * [CameraPermission] needs to be separate from [ListScreenContent] to avoid re-composition
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

@Preview(showSystemUi = true)
@Composable
private fun ListScreenPreview() {
    ComposeTheme {
        ListScreenContent(
            uiModels = persistentListOf(UiModel("1", "name1"), UiModel("2", "name2"), UiModel("3", "name3")),
            isLoading = false,
            onClickSearch = {},
            onItemClick = {},
        )
    }
}
