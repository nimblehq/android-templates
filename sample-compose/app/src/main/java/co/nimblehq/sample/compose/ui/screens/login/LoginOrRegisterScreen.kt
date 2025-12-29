package co.nimblehq.sample.compose.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.ui.common.AppBar
import co.nimblehq.sample.compose.ui.theme.AppTheme
import co.nimblehq.sample.compose.util.AnnotatedStringPart
import co.nimblehq.sample.compose.util.buildAnnotatedStringWithPart

data object LoginOrRegisterScreen

@Composable
fun LoginOrRegisterScreenUi(
    navigateToLogin: () -> Unit,
    navigateToRegister: () -> Unit,
    onClickBack: () -> Unit
) {
    Scaffold(
        topBar = {
            AppBar(
                title = R.string.login_required,
                onClickBack = onClickBack
            )
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(stringResource(R.string.do_you_have_account))

            Button(
                onClick = navigateToLogin,
                modifier = Modifier.padding(top = AppTheme.dimensions.spacingMedium)
            ) {
                Text(text = stringResource(R.string.login))
            }

            Text(
                text = buildAnnotatedStringWithPart(
                    text = stringResource(R.string.have_no_account),
                    annotatedStringPart = AnnotatedStringPart(
                        text = stringResource(R.string.register_here),
                        spanStyle = SpanStyle(
                            color = Color.Blue,
                        ),
                        onClick = navigateToRegister
                    ),
                ),
                modifier = Modifier.padding(top = AppTheme.dimensions.spacingMedium)
            )
        }
    }
}

@Preview
@Composable
private fun LoginOrRegisterScreenUiPreview() {
    LoginOrRegisterScreenUi(
        navigateToLogin = {},
        navigateToRegister = {},
        onClickBack = {}
    )
}
