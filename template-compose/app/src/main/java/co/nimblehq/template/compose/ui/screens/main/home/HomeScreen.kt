@file:Suppress("MatchingDeclarationName")

package co.nimblehq.template.compose.ui.screens.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import co.nimblehq.template.compose.R
import co.nimblehq.template.compose.extensions.collectAsEffect
import co.nimblehq.template.compose.navigation.Navigator
import co.nimblehq.template.compose.ui.base.BaseScreen
import co.nimblehq.template.compose.ui.models.UiModel
import co.nimblehq.template.compose.ui.showToast
import co.nimblehq.template.compose.ui.theme.AppTheme.dimensions
import co.nimblehq.template.compose.ui.theme.ComposeTheme
import kotlinx.collections.immutable.*
import timber.log.Timber

data object Home : NavKey

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: Navigator,
) = BaseScreen {
    val context = LocalContext.current
    viewModel.error.collectAsEffect { e -> e.showToast(context) }
    viewModel.navigator.collectAsEffect { destination -> navigator.goTo(destination) }

    val uiModels: ImmutableList<UiModel> by viewModel.uiModels.collectAsStateWithLifecycle()

    HomeScreenContent(
        title = stringResource(id = R.string.app_name),
        uiModels = uiModels
    )
}

@Composable
private fun HomeScreenContent(
    title: String,
    uiModels: ImmutableList<UiModel>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = dimensions.spacingMedium)
        )
    }
    Timber.d("Result : $uiModels")
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    ComposeTheme {
        HomeScreenContent(
            title = stringResource(id = R.string.app_name),
            uiModels = persistentListOf(UiModel(1), UiModel(2), UiModel(3))
        )
    }
}
