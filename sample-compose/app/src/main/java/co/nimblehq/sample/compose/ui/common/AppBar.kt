package co.nimblehq.sample.compose.ui.common

import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.ui.theme.AppTheme
import co.nimblehq.sample.compose.ui.theme.ComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(title)) },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = AppTheme.colors.topAppBarBackground
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun AppBarPreview() {
    ComposeTheme { AppBar(R.string.home_title_bar) }
}
