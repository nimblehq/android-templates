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
import androidx.navigation3.runtime.NavKey
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
import kotlinx.serialization.Serializable

@Serializable
sealed class DetailsScreen: NavKey {
    @Serializable
    data class Details(val id: Int) : DetailsScreen()
    @Serializable
    data class Search(val username: String) : DetailsScreen()
}

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
    val isFromDeepLink by viewModel.isFromDeepLink.collectAsStateWithLifecycle()

    viewModel.error.collectAsEffect { e -> e.showToast(context) }

    viewModel.onLoginRequired.collectAsEffect {
        navigateToLoginOrRegister()
    }

    viewModel.username.collectAsEffect { username ->
        if (username.isNotNullOrBlank()) context.showToast(context.getString(R.string.welcome_back, username))
    }

    DetailsScreenUiContent(
        uiModel = uiModel,
        isFromDeepLink = isFromDeepLink,
        isLiked = isLiked,
        isLoading = isLoading,
        onClickBack = onClickBack,
        onClickLike = viewModel::onClickLike
    )
}

@Composable
private fun DetailsScreenUiContent(
    uiModel: UiModel?,
    isFromDeepLink: Boolean,
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
                    if (!isFromDeepLink) {
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
            isFromDeepLink = params.isFromDeepLink,
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
            ),
            Params(
                uiModel = UiModel(
                    id = "1",
                    username = "John"
                ),
                isLiked = true,
            ),
            Params(isLoading = true,),
            Params(
                uiModel = UiModel(
                    id = "1",
                    username = "John"
                ),
                isFromDeepLink = true,
            ),
        )

    class Params(
        val uiModel: UiModel? = null,
        val isFromDeepLink: Boolean = false,
        val isLiked: Boolean = false,
        val isLoading: IsLoading = false,
    )
}
