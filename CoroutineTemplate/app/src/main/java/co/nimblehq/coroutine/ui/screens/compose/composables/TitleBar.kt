package co.nimblehq.coroutine.ui.screens.compose.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import co.nimblehq.coroutine.ui.screens.compose.theme.Dimension

@Suppress("LongMethod")
@ExperimentalComposeUiApi
@Composable
fun TitleBar(
    title: String,
    textFieldValue: String,
    onTextFieldValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimension.Dp24)
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current

        Text(
            text = title,
            style = MaterialTheme.typography.h6
        )
        TextField(
            value = textFieldValue,
            onValueChange = onTextFieldValueChange,
            label = { Text(text = "Demo TextField") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() },
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimension.Dp16)
        )
    }
}
