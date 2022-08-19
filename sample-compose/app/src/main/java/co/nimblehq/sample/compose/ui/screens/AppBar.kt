package co.nimblehq.sample.compose.ui.screens

import androidx.annotation.StringRes
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.ui.theme.ComposeSampleTheme

@Composable
fun AppBar(@StringRes title: Int) {
    TopAppBar(
        title = { Text(text = stringResource(title)) }
    )
}

@Preview(showBackground = true)
@Composable
fun AppBarPreview() {
    ComposeSampleTheme { AppBar(R.string.home_title) }
}
