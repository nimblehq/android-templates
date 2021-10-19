package co.nimblehq.coroutine.ui.screens.compose.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import co.nimblehq.coroutine.domain.model.UserUiModel
import co.nimblehq.coroutine.ui.screens.compose.theme.ComposeTheme

@Suppress("LongMethod", "LongParameterList")
@ExperimentalComposeUiApi
@Composable
fun ComposeScreen(
    userUiModels: List<UserUiModel>,
    showLoading: Boolean,
    textFieldValue: String,
    onTextFieldValueChange: (String) -> Unit,
    onUserItemClick: (String) -> Unit
) {
    ComposeTheme {
        Scaffold(
            topBar = {
                TitleBar(
                    title = "Jetpack Compose",
                    textFieldValue = textFieldValue,
                    onTextFieldValueChange = onTextFieldValueChange
                )
            }
        ) {
            ContentCard {
                Box {
                    UserList(
                        userUiModels = userUiModels,
                        onUserItemClick = onUserItemClick
                    )
                    if (showLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(alignment = Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}
