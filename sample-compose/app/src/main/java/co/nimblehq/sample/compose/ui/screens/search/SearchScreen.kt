package co.nimblehq.sample.compose.ui.screens.search

import android.content.ClipData
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.ui.base.BaseScreen
import co.nimblehq.sample.compose.ui.common.AppBar
import co.nimblehq.sample.compose.ui.common.PATH_BASE
import co.nimblehq.sample.compose.ui.common.PATH_SEARCH
import co.nimblehq.sample.compose.ui.theme.AppTheme.dimensions
import co.nimblehq.sample.compose.ui.theme.ComposeTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data object SearchScreen : NavKey

@Composable
fun SearchScreen(
    onClickCreateDeeplink: (String) -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
) = BaseScreen(
    isDarkStatusBarIcons = true,
) {
    var username by remember { mutableStateOf("") }
    val clipboardManager = LocalClipboard.current
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        topBar = {
            AppBar(
                title = R.string.search_title,
                onClickBack = onClickBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(dimensions.spacingLarge),
            verticalArrangement = Arrangement.spacedBy(dimensions.spacingMedium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(R.string.enter_username)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(dimensions.spacingMedium))

            Button(
                onClick = { onClickCreateDeeplink(username) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.create_deeplink))
            }

            Spacer(modifier = Modifier.height(dimensions.spacingMedium))

            // Show deeplink preview
            if (username.isNotEmpty()) {
                val deeplink = "$PATH_BASE/$PATH_SEARCH?username=$username"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Deeplink preview:\n$deeplink",
                        modifier = Modifier
                            .padding(dimensions.spacingMedium)
                            .weight(1f)
                    )
                    IconButton(onClick = {
                        scope.launch {
                            clipboardManager.setClipEntry(
                                ClipData.newPlainText("deeplink", deeplink).toClipEntry(),
                            )
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Copy deeplink",
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SearchScreenPreview() {
    ComposeTheme {
        SearchScreen(
            onClickCreateDeeplink = {},
            onClickBack = {}
        )
    }
}
