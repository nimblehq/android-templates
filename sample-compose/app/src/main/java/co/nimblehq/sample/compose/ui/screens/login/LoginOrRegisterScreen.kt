package co.nimblehq.sample.compose.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.ui.base.BaseScreen
import co.nimblehq.sample.compose.ui.common.AppBar
import co.nimblehq.sample.compose.ui.theme.AppTheme.dimensions
import co.nimblehq.sample.compose.ui.theme.ComposeTheme
import co.nimblehq.sample.compose.util.buildAnnotatedStringWithPart
import kotlinx.serialization.Serializable

@Serializable
data object LoginOrRegisterScreen : NavKey

@Composable
fun LoginOrRegisterScreen(
    navigateToLogin: () -> Unit,
    navigateToRegister: () -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
) = BaseScreen(
    isDarkStatusBarIcons = true,
) {
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

            Text(
                text = stringResource(R.string.login_required),
                modifier = Modifier.padding(dimensions.spacingMedium)
            )

            Button(
                onClick = navigateToLogin,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.login))
            }

            OutlinedButton(
                onClick = navigateToRegister,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.register_here))
            }

            Spacer(modifier = Modifier.height(dimensions.spacingMedium))

            val fullText = stringResource(R.string.do_you_have_account)
            val clickablePart = stringResource(R.string.register_here)
            val annotatedString = buildAnnotatedStringWithPart(
                fullText = fullText,
                clickablePart = clickablePart,
                clickableStyle = SpanStyle(
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold
                ),
                onClick = navigateToRegister,
            )

            Text(text = annotatedString)

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginOrRegisterScreenPreview() {
    ComposeTheme {
        LoginOrRegisterScreen(
            navigateToLogin = {},
            navigateToRegister = {},
            onClickBack = {}
        )
    }
}
