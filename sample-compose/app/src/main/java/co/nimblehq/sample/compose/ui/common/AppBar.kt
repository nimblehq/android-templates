package co.nimblehq.sample.compose.ui.common

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
    onClickBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            onClickBack?.let {
                IconButton(onClick = onClickBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
            }
        },
        title = { Text(text = stringResource(title)) },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = AppTheme.colors.topAppBarBackground
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun AppBarPreview() {
    ComposeTheme { AppBar(R.string.list_title) }
}
