package co.nimblehq.template.compose.ui.screens.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.nimblehq.template.compose.R
import co.nimblehq.template.compose.extensions.collectAsEffect
import co.nimblehq.template.compose.ui.base.BaseDestination
import co.nimblehq.template.compose.ui.base.BaseScreen
import co.nimblehq.template.compose.ui.models.UiModel
import co.nimblehq.template.compose.ui.showToast
import co.nimblehq.template.compose.ui.theme.AppTheme.dimensions
import co.nimblehq.template.compose.ui.theme.ComposeTheme
import timber.log.Timber

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: (destination: BaseDestination) -> Unit,
) = BaseScreen {
    val context = LocalContext.current
    viewModel.error.collectAsEffect { e -> e.showToast(context) }
    viewModel.navigator.collectAsEffect { destination -> navigator(destination) }

    val uiModels: List<UiModel> by viewModel.uiModels.collectAsStateWithLifecycle()

    HomeScreenContent(
        title = stringResource(id = R.string.app_name),
        uiModels = uiModels
    )
}

@Composable
private fun HomeScreenContent(
    title: String,
    uiModels: List<UiModel>
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
                .padding(all = dimensions.spacingMedium)
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
