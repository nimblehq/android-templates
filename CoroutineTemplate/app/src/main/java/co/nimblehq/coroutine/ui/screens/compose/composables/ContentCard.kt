package co.nimblehq.coroutine.ui.screens.compose.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import co.nimblehq.coroutine.ui.screens.compose.theme.Dimen

@Composable
fun ContentCard(
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(
            topStart = Dimen.Dp24,
            topEnd = Dimen.Dp24,
            bottomStart = Dimen.Dp0,
            bottomEnd = Dimen.Dp0
        ),
        elevation = Dimen.Dp0,
        modifier = Modifier.fillMaxSize()
    ) {
        content.invoke()
    }
}
