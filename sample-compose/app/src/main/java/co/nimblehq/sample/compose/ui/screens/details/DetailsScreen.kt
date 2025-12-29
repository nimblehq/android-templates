package co.nimblehq.sample.compose.ui.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.nimblehq.common.extensions.isNotNullOrBlank
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.extensions.collectAsEffect
import co.nimblehq.sample.compose.extensions.showToast
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
    navigateToLoginOrRegister: () -> Unit,
    onClickBack: () -> Unit
) {
    val context = LocalContext.current

    val uiModel by viewModel.uiModel.collectAsStateWithLifecycle()
    val isLiked by viewModel.isLiked.collectAsStateWithLifecycle()
    val isLoading: IsLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    viewModel.error.collectAsEffect { e -> e.showToast(context) }

    viewModel.onLoginRequired.collectAsEffect {
        navigateToLoginOrRegister()
    }

    viewModel.userName.collectAsEffect { userName ->
        if (userName.isNotNullOrBlank()) context.showToast(context.getString(R.string.welcome_back, userName))
    }

    DetailsScreenUiContent(
        uiModel = uiModel,
        isLiked = isLiked,
        isLoading = isLoading,
        onClickBack = onClickBack,
        onClickLike = viewModel::onClickLike
    )
}

@Composable
private fun DetailsScreenUiContent(
    uiModel: UiModel?,
    isLiked: Boolean,
    isLoading: IsLoading,
    onClickBack: () -> Unit,
    onClickLike: () -> Unit
) {
    Scaffold(
        topBar = {
            AppBar(
                title = R.string.details_title,
                onClickBack = onClickBack,
                actions = {
                    IconButton(
                        onClick = onClickLike,
                        modifier = Modifier.testTag(stringResource(R.string.test_tag_favorite_button))
                    ) {
                        Icon(
                            if (isLiked) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                            contentDescription = null,
                            tint = if (isLiked) Color.Red else LocalContentColor.current
                        )
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
private fun DetailsScreenUiContentPreview(
    @PreviewParameter(DetailsScreenPreviewParametersProvider::class)
    params: DetailsScreenPreviewParametersProvider.Params
) {
    ComposeTheme {
        DetailsScreenUiContent(
            uiModel = params.uiModel,
            isLiked = params.isLiked,
            isLoading = params.isLoading,
            onClickBack = {},
            onClickLike = {}
        )
    }
}

private class DetailsScreenPreviewParametersProvider :
    PreviewParameterProvider<DetailsScreenPreviewParametersProvider.Params> {
    override val values: Sequence<Params>
        get() = sequenceOf(
            Params(
                uiModel = UiModel(
                    id = "1",
                    username = "John"
                ),
                isLiked = false,
                isLoading = false,
            ),
            Params(
                uiModel = UiModel(
                    id = "1",
                    username = "John"
                ),
                isLiked = true,
                isLoading = false,
            ),
            Params(
                uiModel = null,
                isLiked = false,
                isLoading = true,
            ),
        )

    class Params(
        val uiModel: UiModel?,
        val isLiked: Boolean,
        val isLoading: IsLoading,
    )
}
