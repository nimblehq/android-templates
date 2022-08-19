package co.nimblehq.sample.compose.ui.screens.second

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.ui.screens.AppBar
import co.nimblehq.sample.compose.ui.theme.ComposeSampleTheme

@Composable
fun SecondScreen(uiModel: UiModel) {
    Scaffold(topBar = {
        AppBar(R.string.second_title)
    }) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = stringResource(R.string.id_title, uiModel.id),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondScreenPreview() {
    ComposeSampleTheme {
        SecondScreen(UiModel(1))
    }
}
