package co.nimblehq.template.compose.common.ui

import androidx.compose.runtime.Composable

@Composable
fun BaseScreen(
    content: @Composable () -> Unit,
) {
    content()
}
