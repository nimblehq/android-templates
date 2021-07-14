package co.nimblehq.coroutine.ui.screens.compose.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Suppress("MagicNumber")
@Composable
fun ContentCard(
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(
            topStart = 24.dp,
            topEnd = 24.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        elevation = 0.dp,
        modifier = Modifier.fillMaxSize()
    ) {
        content.invoke()
    }
}
