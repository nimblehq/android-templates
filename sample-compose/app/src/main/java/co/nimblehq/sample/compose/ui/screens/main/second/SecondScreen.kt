package co.nimblehq.sample.compose.ui.screens.main.second

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.ui.base.BaseDestination
import co.nimblehq.sample.compose.ui.base.KeyResultOk
import co.nimblehq.sample.compose.ui.common.AppBar
import co.nimblehq.sample.compose.ui.theme.AppTheme.dimensions
import co.nimblehq.sample.compose.ui.theme.ComposeTheme

@Composable
fun SecondScreen(
    viewModel: SecondViewModel = hiltViewModel(),
    navigator: (destination: BaseDestination) -> Unit,
    id: String,
) {
    SecondScreenContent(
        id = id,
        onUpdateClick = {
            navigator(BaseDestination.Up().addResult(KeyResultOk, true))
        },
    )
}

@Composable
private fun SecondScreenContent(
    id: String,
    onUpdateClick: () -> Unit,
) {
    Scaffold(topBar = {
        AppBar(R.string.second_title_bar)
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = stringResource(R.string.second_id_title, id),
                )

                Button(
                    onClick = { onUpdateClick() },
                    modifier = Modifier.padding(dimensions.spacingMedium)
                ) {
                    Text(
                        text = stringResource(R.string.second_update)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SecondScreenPreview() {
    ComposeTheme {
        SecondScreenContent(
            id = "1",
            onUpdateClick = {},
        )
    }
}
