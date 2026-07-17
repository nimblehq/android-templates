package co.nimblehq.template.compose.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import co.nimblehq.template.compose.R
import co.nimblehq.template.compose.common.ErrorEvent
import co.nimblehq.template.compose.common.NavigationEvent
import co.nimblehq.template.compose.common.ui.BaseScreen
import co.nimblehq.template.compose.extensions.collectAsEffect
import co.nimblehq.template.compose.ui.showToast
import co.nimblehq.template.compose.ui.theme.AppTheme.dimensions
import co.nimblehq.template.compose.ui.theme.ComposeTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: (NavKey) -> Unit,
) = BaseScreen {
    val context = LocalContext.current

    viewModel.events.collectAsEffect { event ->
        when (event) {
            is NavigationEvent -> onNavigate(event.destination)
            is ErrorEvent -> event.error.showToast(context)
        }
    }

    HomeScreenContent(
        title = stringResource(id = R.string.app_name),
        onNavigateToList = { viewModel.setIntent(HomeIntent.NavigateToList) },
    )
}

@Composable
private fun HomeScreenContent(
    title: String,
    onNavigateToList: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = dimensions.spacingMedium)
        )
        Button(
            modifier = Modifier.wrapContentWidth(),
            onClick = onNavigateToList,
        ) {
            Text(
                text = stringResource(id = R.string.home_navigate_to_list),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    ComposeTheme {
        HomeScreenContent(
            title = stringResource(id = R.string.app_name),
            onNavigateToList = {}
        )
    }
}
