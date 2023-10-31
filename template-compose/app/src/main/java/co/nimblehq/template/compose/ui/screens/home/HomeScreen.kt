package co.nimblehq.kmm.template.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.nimblehq.kmm.template.R
import co.nimblehq.kmm.template.Strings
import co.nimblehq.kmm.template.extensions.collectAsEffect
import co.nimblehq.kmm.template.getPlatform
import co.nimblehq.kmm.template.sharedres.SharedRes
import co.nimblehq.kmm.template.ui.AppDestination
import co.nimblehq.kmm.template.ui.models.UiModel
import co.nimblehq.kmm.template.ui.showToast
import co.nimblehq.kmm.template.ui.theme.AppTheme.dimensions
import co.nimblehq.kmm.template.ui.theme.ComposeTheme
import org.koin.androidx.compose.getViewModel
import timber.log.Timber

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = getViewModel(),
    navigator: (destination: AppDestination) -> Unit,
) {
    val context = LocalContext.current
    viewModel.error.collectAsEffect { e -> e.showToast(context) }
    viewModel.navigator.collectAsEffect { destination -> navigator(destination) }

    val uiModels: List<UiModel> by viewModel.uiModels.collectAsStateWithLifecycle()

    HomeScreenContent(
        title = Strings(LocalContext.current).get(
            id = SharedRes.strings.greeting,
            args = listOf(getPlatform().name)
        ),
        uiModels = uiModels
    )
}

@Composable
private fun HomeScreenContent(
    title: String,
    uiModels: List<UiModel>,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = dimensions.spacingNormal)
        )
    }
    Timber.d("Result : $uiModels")
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    ComposeTheme {
        HomeScreenContent(
            title = stringResource(id = R.string.app_name),
            uiModels = listOf(UiModel(1), UiModel(2), UiModel(3))
        )
    }
}
