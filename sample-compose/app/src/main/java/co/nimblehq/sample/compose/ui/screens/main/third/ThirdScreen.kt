package co.nimblehq.sample.compose.ui.screens.main.third

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.navigation.Navigator
import co.nimblehq.sample.compose.ui.base.BaseScreen
import co.nimblehq.sample.compose.ui.common.AppBar
import co.nimblehq.sample.compose.ui.models.UiModel
import co.nimblehq.sample.compose.ui.theme.ComposeTheme

@Suppress("UnusedPrivateMember")
@Composable
fun ThirdScreen(
    model: UiModel?,
    navigator: Navigator,
    viewModel: ThirdViewModel = hiltViewModel(),
) = BaseScreen(
    isDarkStatusBarIcons = true,
) {
    ThirdScreenContent(data = model)
}

@Composable
private fun ThirdScreenContent(
    data: UiModel?,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            AppBar(title = R.string.third_title_bar)
        },
        modifier = modifier,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = stringResource(R.string.third_data_title, data.toString()),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
private fun ThirdScreenPreview() {
    ComposeTheme {
        ThirdScreenContent(data = UiModel("1", "name1"))
    }
}
