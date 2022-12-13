package co.nimblehq.template.compose.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import co.nimblehq.template.compose.ui.AppDestination
import co.nimblehq.template.compose.R
import co.nimblehq.template.compose.model.UiModel
import co.nimblehq.template.compose.ui.theme.Dimension.SpacingNormal
import co.nimblehq.template.compose.ui.theme.ComposeTheme
import timber.log.Timber

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: (destination: AppDestination) -> Unit
) {
    val uiModels: List<UiModel> by viewModel.uiModels.collectAsState()

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
    Surface {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentHeight()
                .padding(all = SpacingNormal)
        )
    }
    Timber.d("Result : $uiModels")
}

@Composable
@Preview(showSystemUi = true)
private fun HomeComposeScreenPreview() {
    ComposeTheme {
        HomeScreenContent(
            title = stringResource(id = R.string.app_name),
            uiModels = listOf(UiModel(1), UiModel(2), UiModel(3))
        )
    }
}
