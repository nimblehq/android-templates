package co.nimblehq.sample.compose.ui.screens

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import co.nimblehq.sample.compose.R

@Composable
fun AppBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) }
    )
}
