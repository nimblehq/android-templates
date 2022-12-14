package co.nimblehq.sample.compose.ui.screens.home

import android.Manifest.permission.*
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.lib.IsLoading
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.ui.AppDestination
import co.nimblehq.sample.compose.ui.screens.AppBar
import co.nimblehq.sample.compose.ui.theme.ComposeTheme
import co.nimblehq.sample.compose.ui.userReadableMessage
import timber.log.Timber

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: (destination: AppDestination) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(viewModel.error) {
        viewModel.error.collect { error ->
            val message = error.userReadableMessage(context)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
    LaunchedEffect(viewModel.navigator) {
        viewModel.navigator.collect { destination -> navigator(destination) }
    }

    val showLoading: IsLoading by viewModel.showLoading.collectAsState()
    val uiModels: List<UiModel> by viewModel.uiModels.collectAsState()

    HomeScreenContent(
        uiModels = uiModels,
        showLoading = showLoading,
        onItemClick = viewModel::navigateToSecond
    )
}

@Composable
private fun HomeScreenContent(
    uiModels: List<UiModel>,
    showLoading: IsLoading,
    onItemClick: (UiModel) -> Unit
) {
    val permissions = arrayOf(
        ACCESS_COARSE_LOCATION,
        ACCESS_FINE_LOCATION,
        CAMERA
    )
    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            Timber.d("${permissionsMap.keys} granted")
        } else {
            Timber.d("Request cancelled, missing permissions in manifest or denied permanently")
        }
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        checkAndRequestLocationPermissions(
            context,
            permissions,
            launcherMultiplePermissions
        )
    }

    Scaffold(topBar = {
        AppBar(R.string.home_title_bar)
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (showLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                ItemList(
                    uiModels = uiModels,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

fun checkAndRequestLocationPermissions(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
) {
    if (
        permissions.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    ) {
        // Permissions are already granted
    } else {
        // Request permissions
        launcher.launch(permissions)
    }
}

@Composable
@Preview(showSystemUi = true)
private fun HomeScreenPreview() {
    ComposeTheme {
        HomeScreenContent(
            uiModels = listOf(UiModel("1"), UiModel("2"), UiModel("3")),
            showLoading = false,
            onItemClick = {}
        )
    }
}
