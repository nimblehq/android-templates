package co.nimblehq.sample.compose.ui.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.extensions.collectAsEffect
import co.nimblehq.sample.compose.lib.IsLoading
import co.nimblehq.sample.compose.ui.common.AppBar
import co.nimblehq.sample.compose.ui.models.UiModel
import co.nimblehq.sample.compose.ui.screens.list.Item
import co.nimblehq.sample.compose.ui.showToast
import co.nimblehq.sample.compose.ui.theme.ComposeTheme

data class DetailsScreen(val id: Int)

@Composable
fun DetailsScreenUi(
    viewModel: DetailsViewModel,
    onClickBack: () -> Unit
) {
    val context = LocalContext.current

    val uiModel by viewModel.uiModel.collectAsStateWithLifecycle()
    val isLoading: IsLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    viewModel.error.collectAsEffect { e -> e.showToast(context) }

    DetailsScreenUiContent(
        uiModel = uiModel,
        isLoading = isLoading,
        onClickBack = onClickBack
    )
}

@Composable
private fun DetailsScreenUiContent(
    uiModel: UiModel?,
    isLoading: IsLoading,
    onClickBack: () -> Unit
) {
    Scaffold(
        topBar = {
            AppBar(
                title = R.string.details_title,
                onClickBack = onClickBack,
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
                uiModel?.let {
                    Item(
                        uiModel = uiModel,
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DetailsScreenUiContentPreview() {
    ComposeTheme {
        DetailsScreenUiContent(
            uiModel = UiModel(
                id = "1",
                username = "John"
            ),
            isLoading = false,
            onClickBack = {}
        )
    }
}
