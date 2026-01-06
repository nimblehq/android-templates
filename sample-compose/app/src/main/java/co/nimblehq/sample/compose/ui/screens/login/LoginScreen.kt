package co.nimblehq.sample.compose.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.ui.common.AppBar
import co.nimblehq.sample.compose.ui.theme.AppTheme

data object LoginScreen: NavKey

@Composable
fun LoginScreenUi(
    navigateToDetails: (username: String) -> Unit,
    onClickBack: () -> Unit
) {
    Scaffold(
        topBar = {
            AppBar(
                title = R.string.login,
                onClickBack = onClickBack
            )
        }
    ) { paddingValues ->
        var username by remember { mutableStateOf("") }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TextField(
                value = username,
                onValueChange = {
                    username = it
                },
                label = { Text(text = stringResource(R.string.username)) },
                modifier = Modifier.testTag(stringResource(R.string.test_tag_user_name_field))
            )

            Button(
                onClick = {
                    navigateToDetails(username)
                },
                modifier = Modifier
                    .padding(top = AppTheme.dimensions.spacingMedium)
                    .testTag(stringResource(R.string.test_tag_login_button))
            ) {
                Text(text = stringResource(R.string.login))
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenUiPreview() {
    LoginScreenUi(
        navigateToDetails = {},
        onClickBack = {}
    )
}
