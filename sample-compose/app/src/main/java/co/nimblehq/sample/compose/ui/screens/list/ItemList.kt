package co.nimblehq.sample.compose.ui.screens.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.sample.compose.ui.models.UiModel
import co.nimblehq.sample.compose.ui.theme.ComposeTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ItemList(
    uiModels: ImmutableList<UiModel>,
    onItemClick: (UiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        items(uiModels) { uiModel ->
            Item(
                uiModel = uiModel,
                onClick = onItemClick,
            )
            HorizontalDivider()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemListPreview() {
    ComposeTheme {
        ItemList(
            uiModels = persistentListOf(UiModel("1", "name1"), UiModel("2", "name2"), UiModel("3", "name3")),
            onItemClick = {},
        )
    }
}
