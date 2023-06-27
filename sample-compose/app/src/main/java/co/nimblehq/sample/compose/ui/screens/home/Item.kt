package co.nimblehq.sample.compose.ui.screens.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.ui.theme.*
import co.nimblehq.sample.compose.ui.theme.AppTheme.dimensions

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Item(
    uiModel: UiModel,
    onClick: (UiModel) -> Unit,
    onLongClick: (UiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onClick(uiModel) },
                onLongClick = { expanded = true }
            )
    ) {
        Row {
            Text(
                modifier = Modifier
                    .padding(dimensions.spacingNormal)
                    .weight(1f),
                text = uiModel.id
            )
            Text(
                modifier = Modifier
                    .padding(dimensions.spacingNormal)
                    .weight(2f),
                text = uiModel.userName
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = { onLongClick(uiModel) }) {
                Text(stringResource(id = R.string.third_edit_menu_title))
            }
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
            onLongClick = {}
        )
    }
}
