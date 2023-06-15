package co.nimblehq.sample.compose.ui.screens.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.ui.theme.ComposeTheme

@Composable
fun ItemList(
    uiModels: List<UiModel>,
    onItemClick: (UiModel) -> Unit,
    onItemLongClick: (UiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        items(uiModels) { uiModel ->
            Item(
                uiModel = uiModel,
                onClick = onItemClick,
                onLongClick = onItemLongClick
            )
            Divider()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemListPreview() {
    ComposeTheme {
        ItemList(
            uiModels = listOf(UiModel("1"), UiModel("2"), UiModel("3")),
            onItemClick = {},
            onItemLongClick = {}
        )
    }
}
