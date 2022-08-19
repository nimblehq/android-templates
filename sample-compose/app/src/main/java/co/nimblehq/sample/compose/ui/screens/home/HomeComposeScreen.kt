package co.nimblehq.sample.compose.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.ui.screens.AppBar
import co.nimblehq.sample.compose.ui.theme.ComposeSampleTheme

// TODO: Rename to 'HomeScreen'
@Composable
fun HomeComposeScreen(
    uiModels: List<UiModel>,
    showLoading: Boolean,
    onItemClick: (UiModel) -> Unit
) {
    Scaffold(topBar = {
        AppBar(R.string.home_title_bar)
    }) {
        Box(modifier = Modifier.fillMaxSize()) {
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

@Preview(showBackground = true)
@Composable
private fun HomeComposeScreenPreview() {
    ComposeSampleTheme {
        HomeComposeScreen(
            uiModels = listOf(UiModel("1"), UiModel("2"), UiModel("3")),
            showLoading = false,
            onItemClick = {}
        )
    }
}
