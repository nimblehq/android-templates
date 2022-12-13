package co.nimblehq.sample.compose.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.lib.IsLoading
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.ui.AppDestination
import co.nimblehq.sample.compose.ui.screens.AppBar
import co.nimblehq.sample.compose.ui.theme.ComposeTheme
import co.nimblehq.sample.compose.ui.userReadableMessage

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

@Composable
@Preview(showSystemUi = true)
private fun HomeComposeScreenPreview() {
    ComposeTheme {
        HomeScreenContent(
            uiModels = listOf(UiModel("1"), UiModel("2"), UiModel("3")),
            showLoading = false,
            onItemClick = {}
        )
    }
}
