package co.nimblehq.sample.compose.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.ui.common.AppBar
import co.nimblehq.sample.compose.ui.theme.AppTheme
import co.nimblehq.sample.compose.ui.theme.ComposeTheme

data object SearchScreen : NavKey

@Composable
fun SearchScreenUi(
    usernameState: TextFieldState = rememberTextFieldState(),
    onClickBack: () -> Unit,
    onClickCreateDeeplink: (username: String) -> Unit
) {
    Scaffold(
        topBar = {
            AppBar(
                title = R.string.search_title,
                onClickBack = onClickBack
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            TextField(
                state = usernameState,
                lineLimits = TextFieldLineLimits.SingleLine,
                placeholder = { Text(stringResource(R.string.enter_username)) }
            )

            Button(
                onClick = {
                    onClickCreateDeeplink(usernameState.text.toString())
                },
                modifier = Modifier.padding(AppTheme.dimensions.spacingMedium),
            ) {
                Text(stringResource(R.string.create_deeplink))
            }

            Text("Final deeplink: \nhttps://www.android.nimblehq.co?username=${usernameState.text}")
        }
    }
}

@Preview
@Composable
private fun SearchScreenUiPreview() {
    ComposeTheme {
        SearchScreenUi(
            usernameState = rememberTextFieldState("john doe"),
            onClickCreateDeeplink = {},
            onClickBack = {},
        )
    }
}
