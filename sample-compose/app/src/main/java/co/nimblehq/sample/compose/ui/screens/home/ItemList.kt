package co.nimblehq.sample.compose.ui.screens.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.ui.theme.ComposeSampleTheme

@Composable
fun ItemList(
    uiModels: List<UiModel>,
    onItemClick: (UiModel) -> Unit
) {
    LazyColumn {
        itemsIndexed(items = uiModels) { index, uiModel ->
            Item(
                uiModel = uiModel,
                onClick = onItemClick
            )
            if (index != uiModels.lastIndex) Divider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemListPreview() {
    ComposeSampleTheme {
        ItemList(
            uiModels = listOf(UiModel(1), UiModel(2), UiModel(3)),
            onItemClick = {}
        )
    }
}
