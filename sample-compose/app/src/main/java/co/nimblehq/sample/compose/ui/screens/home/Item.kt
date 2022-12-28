package co.nimblehq.sample.compose.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.ui.theme.ComposeTheme
import co.nimblehq.sample.compose.ui.theme.SpacingNormal

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
            modifier = Modifier.padding(SpacingNormal),
            text = uiModel.id
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemPreview() {
    ComposeTheme {
        Item(
            uiModel = UiModel("1"),
            onClick = {}
        )
    }
}
