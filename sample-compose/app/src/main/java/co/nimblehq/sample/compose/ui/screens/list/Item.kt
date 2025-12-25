package co.nimblehq.sample.compose.ui.screens.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.sample.compose.ui.models.UiModel
import co.nimblehq.sample.compose.ui.theme.AppTheme.dimensions
import co.nimblehq.sample.compose.ui.theme.ComposeTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Item(
    uiModel: UiModel,
    onClick: (UiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = { onClick(uiModel) },
            )
    ) {
        Row {
            Text(
                modifier = Modifier
                    .padding(dimensions.spacingMedium)
                    .weight(1f),
                text = uiModel.id
            )
            Text(
                modifier = Modifier
                    .padding(dimensions.spacingMedium)
                    .weight(2f),
                text = uiModel.username
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemPreview() {
    ComposeTheme {
        Item(
            uiModel = UiModel("1", "name1"),
            onClick = {},
        )
    }
}
