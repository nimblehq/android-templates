package co.nimblehq.sample.compose.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.ui.screens.AppBar

@Composable
fun HomeComposeScreen(
    uiModels: List<UiModel>,
    showLoading: Boolean,
    onItemClick: (UiModel) -> Unit
) {
    Scaffold(topBar = { AppBar() }) {
        Box(modifier = Modifier.fillMaxSize()) {
            ItemList(
                uiModels = uiModels,
                onItemClick = onItemClick
            )
            if (showLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
