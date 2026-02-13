package co.nimblehq.sample.compose.ui.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.extensions.collectAsEffect
import co.nimblehq.sample.compose.lib.IsLoading
import co.nimblehq.sample.compose.ui.base.BaseScreen
import co.nimblehq.sample.compose.ui.common.AppBar
import co.nimblehq.sample.compose.ui.models.UiModel
import co.nimblehq.sample.compose.ui.showToast
import co.nimblehq.sample.compose.ui.theme.AppTheme.dimensions
import co.nimblehq.sample.compose.ui.theme.ComposeTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.Serializable

@Serializable
sealed class DetailsScreen : NavKey {
    @Serializable
    data class Details(val id: Int) : DetailsScreen()

    @Serializable
    data class Search(val username: String) : DetailsScreen()
}

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    navigateToLoginOrRegister: () -> Unit,
    onClickBack: () -> Unit,
) = BaseScreen(
    isDarkStatusBarIcons = true,
) {
    val context = LocalContext.current
    viewModel.error.collectAsEffect { e -> e.showToast(context) }

    LaunchedEffect(Unit) {
        viewModel.onLoginRequired.collectLatest {
            navigateToLoginOrRegister()
        }
    }

    val isLoading: IsLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val uiModel: UiModel? by viewModel.uiModel.collectAsStateWithLifecycle()
    val isLiked: Boolean by viewModel.isLiked.collectAsStateWithLifecycle()
    val isFromDeepLink: Boolean by viewModel.isFromDeepLink.collectAsStateWithLifecycle()
    val username: String? by viewModel.username.collectAsStateWithLifecycle()

    DetailsScreenContent(
        uiModel = uiModel,
        isLoading = isLoading,
        isLiked = isLiked,
        isFromDeepLink = isFromDeepLink,
        username = username,
        onClickBack = onClickBack,
        onClickLike = viewModel::onClickLike,
    )
}

@Composable
private fun DetailsScreenContent(
    uiModel: UiModel?,
    isLoading: IsLoading,
    isLiked: Boolean,
    isFromDeepLink: Boolean,
    username: String?,
    onClickBack: () -> Unit,
    onClickLike: () -> Unit,
) {
    Scaffold(
        topBar = {
            AppBar(
                title = R.string.details_title,
                onClickBack = onClickBack,
                actions = {
                    if (!isFromDeepLink) {
                        IconButton(onClick = onClickLike) {
                            Icon(
                                imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = stringResource(R.string.test_tag_favorite_button)
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
                uiModel?.let { model ->
                    Column(
                        modifier = Modifier.padding(dimensions.spacingLarge)
                    ) {
                        Text(text = "ID: ${model.id}")
                        Spacer(modifier = Modifier.height(dimensions.spacingMedium))
                        Text(text = "Username: ${model.username}")

                        username?.let {
                            Spacer(modifier = Modifier.height(dimensions.spacingLarge))
                            Text(text = stringResource(R.string.welcome_back, it))
                        }
                    }
                } ?: run {
                    Text(
                        text = "No data available",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(dimensions.spacingLarge)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun DetailsScreenPreview() {
    ComposeTheme {
        DetailsScreenContent(
            uiModel = UiModel("1", "testuser"),
            isLoading = false,
            isLiked = false,
            isFromDeepLink = false,
            username = null,
            onClickBack = {},
            onClickLike = {},
        )
    }
}
