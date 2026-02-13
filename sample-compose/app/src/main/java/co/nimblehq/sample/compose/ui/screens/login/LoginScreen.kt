package co.nimblehq.sample.compose.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.ui.base.BaseScreen
import co.nimblehq.sample.compose.ui.common.AppBar
import co.nimblehq.sample.compose.ui.theme.AppTheme.dimensions
import co.nimblehq.sample.compose.ui.theme.ComposeTheme
import kotlinx.serialization.Serializable

@Serializable
data object LoginScreen : NavKey

@Composable
fun LoginScreen(
    navigateToDetails: (String) -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
) = BaseScreen(
    isDarkStatusBarIcons = true,
) {
    var username by remember { mutableStateOf("") }

    val usernameFieldTag = stringResource(R.string.test_tag_user_name_field)
    val loginButtonTag = stringResource(R.string.test_tag_login_button)

    Scaffold(
        modifier = modifier,
        topBar = {
            AppBar(
                title = R.string.login,
                onClickBack = onClickBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(dimensions.spacingLarge),
            verticalArrangement = Arrangement.spacedBy(dimensions.spacingMedium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(R.string.username)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = usernameFieldTag }
            )

            Spacer(modifier = Modifier.height(dimensions.spacingMedium))

            Button(
                onClick = { navigateToDetails(username) },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = loginButtonTag },
                enabled = username.isNotEmpty()
            ) {
                Text(stringResource(R.string.login))
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    ComposeTheme {
        LoginScreen(
            navigateToDetails = {},
            onClickBack = {}
        )
    }
}
