package co.nimblehq.sample.compose.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.ui.theme.ComposeSampleTheme
import co.nimblehq.sample.compose.ui.theme.Dimension

@Composable
fun Item(
    uiModel: UiModel,
    onClick: (UiModel) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick(uiModel) })
    ) {
        Text(
            modifier = Modifier.padding(Dimension.SpacingNormal),
            text = uiModel.id.toString()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    ComposeSampleTheme {
        Item(
            uiModel = UiModel(1),
            onClick = {}
        )
    }
}
